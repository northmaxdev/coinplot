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
import java.util.Collections;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toUnmodifiableSet;

@Service
public final class FrankfurterService implements ExchangeRatesService {

    // TODO: Log stuff
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
        Objects.requireNonNull(dto, "DTO is null");

        return dto.keySet()
                .stream()
                .map(Currency::getInstance)
                .collect(toUnmodifiableSet());
    }

    @Override
    public Map<DatedExchange, BigDecimal> getExchangeRates(CurrencyExchangeBatch exchangesOfInterest) {
        String endpointUri = serializeExchangeBatchToUri(exchangesOfInterest);

        ExchangeRatesDto dto = restClient.get()
                .uri(endpointUri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(ExchangeRatesDto.class);
        Objects.requireNonNull(dto, "DTO is null");

        Map<DatedExchange, BigDecimal> exchangeRates = HashMap.newHashMap(exchangesOfInterest.size());
        Currency base = Currency.getInstance(dto.base());
        for (var dateEntry : dto.rates().entrySet()) {
            LocalDate date = dateEntry.getKey();
            for (var rateEntry : dateEntry.getValue().entrySet()) {
                Currency target = Currency.getInstance(rateEntry.getKey());
                BigDecimal rate = rateEntry.getValue();
                DatedExchange exchange = new DatedExchange(base, target, date);
                exchangeRates.put(exchange, rate);
            }
        }
        return Collections.unmodifiableMap(exchangeRates);
    }

    private static String serializeExchangeBatchToUri(CurrencyExchangeBatch exchangeBatch) {
        Currency base = exchangeBatch.base();
        LocalDateInterval dateInterval = exchangeBatch.dateInterval();

        String joinedTargetCodes = exchangeBatch.targets()
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
