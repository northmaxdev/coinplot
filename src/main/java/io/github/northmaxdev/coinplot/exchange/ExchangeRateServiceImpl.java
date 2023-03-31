// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.common.core.LocalDateRange;
import io.github.northmaxdev.coinplot.common.web.DTOMapper;
import io.github.northmaxdev.coinplot.config.APIConfig;
import io.github.northmaxdev.coinplot.currency.Currency;
import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.util.stream.Collectors.joining;

@Service
public final class ExchangeRateServiceImpl implements ExchangeRateService {

    private static final Logger LOG = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);

    private final APIConfig apiConfig;
    private final HttpClient httpClient;
    private final ObjectMapper jsonParser;
    private final DTOMapper<ExchangeRatesDTO, Collection<ExchangeRate>> dtoMapper;
    private final StopWatch stopWatch;

    @Autowired
    public ExchangeRateServiceImpl(
            APIConfig apiConfig,
            HttpClient httpClient,
            ObjectMapper jsonParser,
            DTOMapper<ExchangeRatesDTO, Collection<ExchangeRate>> dtoMapper) {
        this.apiConfig = apiConfig;
        this.httpClient = httpClient;
        this.jsonParser = jsonParser;
        this.dtoMapper = dtoMapper;
        this.stopWatch = new StopWatch();
    }

    @Override
    public Collection<ExchangeRate> getExchangeRatesBetweenDates(
            @Nonnull Currency base,
            @Nonnull Collection<Currency> targets,
            @Nonnull LocalDateRange dateRange) {
        URI requestURI = createRequestURI(base, targets, dateRange);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(requestURI)
                .GET()
                .build();

        try {
            stopWatch.reset();
            stopWatch.start();

            // TODO: Profile other response content types, maybe byte[] is faster?
            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            ExchangeRatesDTO dto = jsonParser.readValue(response.body(), ExchangeRatesDTO.class);
            Collection<ExchangeRate> exchangeRates = dtoMapper.map(dto);

            stopWatch.stop();
            String infoMessage = "Fetched %d exchange rates in %dms"
                    .formatted(exchangeRates.size(), stopWatch.getTime(TimeUnit.MILLISECONDS));
            LOG.info(infoMessage);

            return exchangeRates;
        } catch (IOException | InterruptedException e) {
            LOG.error("Failed to fetch and/or deserialize exchange rates: " + e);
            // FIXME: Returning an empty list is a bad idea in terms of API design, maybe simply rethrow exceptions?
            return List.of();
        }
    }

    private @Nonnull URI createRequestURI(
            @Nonnull Currency base,
            @Nonnull Collection<Currency> targets,
            @Nonnull LocalDateRange dateRange) {
        // TODO:
        //  Profile this section and if it proves to be a performance bottleneck, consider spreading serialization of
        //  targets, start and end between 3 threads (though it'll probably be negligible compared to other parts of
        //  the codebase).
        String fmt = apiConfig.getExchangeRatesURIFormat();
        String joinedTargets = targets.stream()
                .map(Currency::threeLetterISOCode)
                .collect(joining(","));

        String s = fmt.formatted(
                ISO_LOCAL_DATE.format(dateRange.start()),
                ISO_LOCAL_DATE.format(dateRange.end()),
                base.threeLetterISOCode(),
                joinedTargets);

        return URI.create(s);
    }
}
