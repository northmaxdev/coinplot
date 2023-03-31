// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.currency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.common.web.DTOMapper;
import io.github.northmaxdev.coinplot.config.APIConfig;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
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
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public final class CurrencyServiceImpl implements CurrencyService {

    private static final Logger LOG = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    private final URI requestURI;
    private final HttpClient httpClient;
    private final ObjectMapper jsonParser;
    private final DTOMapper<Map<String, String>, Set<Currency>> dtoMapper;
    private final StopWatch stopWatch;
    private @Nonnull Set<Currency> cache;

    @Autowired
    public CurrencyServiceImpl(
            APIConfig apiConfig,
            HttpClient httpClient,
            ObjectMapper jsonParser,
            DTOMapper<Map<String, String>, Set<Currency>> dtoMapper) {
        // No need for a CurrencyService instance to keep hold of an APIConfig
        // reference if (in this case) pre-constructing the URI is enough.
        this.requestURI = URI.create(apiConfig.getCurrenciesURI());
        this.httpClient = httpClient;
        this.jsonParser = jsonParser;
        this.dtoMapper = dtoMapper;
        this.stopWatch = new StopWatch();
        this.cache = Set.of();
    }

    @Override
    public Set<Currency> getAvailableCurrencies() {
        fetchIfCacheIsEmpty();
        return cache;
    }

    @Override
    public Optional<Currency> getCurrency(@Nullable String threeLetterISOCode) {
        fetchIfCacheIsEmpty();
        // TODO:
        //  This will most likely be called relatively often, which means performance of this is impactful.
        //  Consider implementing cache as a Map for constant-time access.
        return cache.stream()
                .filter(currency -> Objects.equals(threeLetterISOCode, currency.threeLetterISOCode()))
                .findFirst();
    }

    private void fetchIfCacheIsEmpty() {
        if (cache.isEmpty()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(requestURI)
                    .GET()
                    .build();

            try {
                stopWatch.reset();
                stopWatch.start();

                // TODO: Profile other response content types, maybe byte[] is faster?
                HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
                Map<String, String> dto = jsonParser.readValue(response.body(), new TypeReference<>(){});
                cache = dtoMapper.map(dto);

                stopWatch.stop();
                String infoMessage = "Fetched and cached %d currencies in %dms"
                        .formatted(cache.size(), stopWatch.getTime(TimeUnit.MILLISECONDS));
                LOG.info(infoMessage);
            } catch (IOException | InterruptedException e) {
                LOG.warn("Failed to initialize currency data: " + e);
            }
        }
    }
}
