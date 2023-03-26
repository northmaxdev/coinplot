// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.currency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.config.APIConfig;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
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

@Service
public final class CurrencyService {

    private static final Logger LOG = LoggerFactory.getLogger(CurrencyService.class);

    private final URI requestURI;
    private final HttpClient httpClient;
    private final ObjectMapper jsonParser;
    private final CurrencyDTOMapper dtoMapper;
    private @Nonnull Set<Currency> cache;

    @Autowired
    public CurrencyService(
            APIConfig apiConfig,
            HttpClient httpClient,
            ObjectMapper jsonParser,
            CurrencyDTOMapper dtoMapper) {
        // No need for a CurrencyService instance to keep hold of an APIConfig
        // reference if (in this case) pre-constructing the URI is enough.
        this.requestURI = URI.create(apiConfig.getCurrenciesURI());
        this.httpClient = httpClient;
        this.jsonParser = jsonParser;
        this.dtoMapper = dtoMapper;
        this.cache = Set.of();
    }

    public Set<Currency> getAvailableCurrencies() {
        fetchIfCacheIsEmpty();
        return cache;
    }

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
                // TODO: Profile other response content types, maybe byte[] is faster?
                HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
                Map<String, String> dto = jsonParser.readValue(response.body(), new TypeReference<>(){});

                cache = dtoMapper.map(dto);
                LOG.info("Fetched and cached " + cache.size() + " currencies");
            } catch (IOException | InterruptedException e) {
                LOG.warn("Failed to initialize currency data: " + e);
            }
        }
    }
}
