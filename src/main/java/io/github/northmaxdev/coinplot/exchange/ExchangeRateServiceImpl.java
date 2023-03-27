// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.common.DTOMapper;
import io.github.northmaxdev.coinplot.config.APIConfig;
import io.github.northmaxdev.coinplot.currency.Currency;
import jakarta.annotation.Nonnull;
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
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.util.stream.Collectors.joining;

@Service
public final class ExchangeRateServiceImpl implements ExchangeRateService {

    private static final Logger LOG = LoggerFactory.getLogger(ExchangeRateService.class);

    private final APIConfig apiConfig;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final DTOMapper<ExchangeRatesDTO, List<ExchangeRate>> dtoMapper;

    @Autowired
    public ExchangeRateServiceImpl(
            APIConfig apiConfig,
            HttpClient httpClient,
            ObjectMapper objectMapper,
            DTOMapper<ExchangeRatesDTO, List<ExchangeRate>> dtoMapper) {
        this.apiConfig = apiConfig;
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public List<ExchangeRate> getExchangeRatesBetweenDates(
            @Nonnull Currency base,
            @Nonnull Collection<Currency> targets,
            @Nonnull LocalDate start,
            @Nonnull LocalDate end) {
        URI requestURI = createRequestURI(base, targets, start, end);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(requestURI)
                .GET()
                .build();

        try {
            // TODO: Profile other response content types, maybe byte[] is faster?
            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            ExchangeRatesDTO dto = objectMapper.readValue(response.body(), ExchangeRatesDTO.class);
            return dtoMapper.map(dto);
        } catch (IOException | InterruptedException e) {
            LOG.warn("Failed to acquire exchange rates: " + e);
            return List.of();
        }
    }

    private @Nonnull URI createRequestURI(
            @Nonnull Currency base,
            @Nonnull Collection<Currency> targets,
            @Nonnull LocalDate start,
            @Nonnull LocalDate end) {
        // TODO:
        //  Profile this section and if it proves to be a performance bottleneck, consider spreading serialization of
        //  targets, start and end between 3 threads (though it'll probably be negligible compared to other parts of
        //  the codebase).
        String fmt = apiConfig.getExchangeRatesURIFormat();
        String joinedTargets = targets.stream()
                .map(Currency::threeLetterISOCode)
                .collect(joining(","));

        String s = fmt.formatted(
                start.format(ISO_LOCAL_DATE),
                end.format(ISO_LOCAL_DATE),
                base.threeLetterISOCode(),
                joinedTargets);

        return URI.create(s);
    }
}
