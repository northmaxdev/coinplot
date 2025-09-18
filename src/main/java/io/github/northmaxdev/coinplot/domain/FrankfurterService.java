// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import io.github.northmaxdev.coinplot.langext.LocalDateInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toUnmodifiableSet;

@Service
public final class FrankfurterService implements ExchangeRatesService {

    private static final Logger LOG = LoggerFactory.getLogger(FrankfurterService.class);

    private final RestClient restClient;

    @Autowired
    public FrankfurterService(FrankfurterConfig config) {
        this.restClient = RestClient.create(config.host());
    }

    @Override
    public Set<Currency> getSupportedCurrencies() {
        Map<String, String> dto = restClient.get()
                .uri("/currencies")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        if (dto == null) {
            LOG.error("Received HTTP response but it has no body (currencies)");
            throw new IllegalStateException("DTO is null");
        }

        Set<Currency> currencies = dto.keySet()
                .stream()
                .map(Currency::getInstance)
                .collect(toUnmodifiableSet());

        LOG.info("Fetched and deserialized {} currencies", currencies.size());
        return currencies;
    }

    @Override
    public ExchangeRatesDataset getExchangeRates(DatedExchangeZip exchangesOfInterest) {
        String endpointPath = serializeExchangesToPath(exchangesOfInterest);

        ExchangeRatesDto dto = restClient.get()
                .uri(endpointPath)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(ExchangeRatesDto.class);

        if (dto == null) {
            LOG.error("Received HTTP response but it has no body (exchange rates)");
            throw new IllegalStateException("DTO is null");
        }

        Map<DatedExchange, BigDecimal> exchangeRates = HashMap.newHashMap(exchangesOfInterest.size());

        Currency base = Currency.getInstance(dto.base());
        for (var dateEntry : dto.rates().entrySet()) {
            // Consideration: launch a virtual thread for each date,
            // see if that makes a performance difference for large datasets compared to current impl
            LocalDate date = dateEntry.getKey();
            for (var rateEntry : dateEntry.getValue().entrySet()) {
                Currency target = Currency.getInstance(rateEntry.getKey());
                BigDecimal rate = rateEntry.getValue();
                DatedExchange exchange = new DatedExchange(base, target, date);
                exchangeRates.put(exchange, rate);
            }
        }

        LOG.info("Fetched and deserialized {} exchange rates", exchangeRates.size());
        return new ExchangeRatesDataset(exchangeRates);
    }

    private static String serializeExchangesToPath(DatedExchangeZip exchangesOfInterest) {
        Currency base = exchangesOfInterest.base();
        LocalDateInterval dateInterval = exchangesOfInterest.dateInterval();

        String joinedTargetCodes = exchangesOfInterest.targets()
                .stream()
                .map(Currency::getCurrencyCode)
                .collect(joining(","));

        return "/%s..%s?base=%s&symbols=%s".formatted(
                DateTimeFormatter.ISO_LOCAL_DATE.format(dateInterval.start()),
                DateTimeFormatter.ISO_LOCAL_DATE.format(dateInterval.end()),
                base.getCurrencyCode(),
                joinedTargetCodes
        );
    }
}
