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
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public final class CurrencyServiceImpl implements CurrencyService {

    private static final Logger LOG = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    private final URI requestURI;
    private final HttpClient httpClient;
    private final ObjectMapper jsonParser;
    private final DTOMapper<Map<String, String>, Map<String, Currency>> dtoMapper;
    private final StopWatch stopWatch;
    private @Nonnull Map<String, Currency> cache;

    @Autowired
    public CurrencyServiceImpl(
            APIConfig apiConfig,
            HttpClient httpClient,
            ObjectMapper jsonParser,
            DTOMapper<Map<String, String>, Map<String, Currency>> dtoMapper) {
        // No need for a CurrencyService instance to keep hold of an APIConfig
        // reference if (in this case) pre-constructing the URI is enough.
        this.requestURI = URI.create(apiConfig.getCurrenciesURI());
        this.httpClient = httpClient;
        this.jsonParser = jsonParser;
        this.dtoMapper = dtoMapper;
        this.stopWatch = new StopWatch();
        this.cache = Map.of();
    }

    @Override
    public Collection<Currency> getAvailableCurrencies() throws Exception {
        fetchIfCacheIsEmpty();
        return cache.values();
    }

    @Override
    public Optional<Currency> getCurrency(@Nullable String threeLetterISOCode) throws Exception {
        fetchIfCacheIsEmpty();
        return Optional.ofNullable(threeLetterISOCode)
                .map(cache::get);
    }

    private void fetchIfCacheIsEmpty() throws IOException, InterruptedException {
        if (cache.isEmpty()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(requestURI)
                    .GET()
                    .build();

            try {
                stopWatch.reset();
                stopWatch.start();

                HttpResponse<byte[]> response = httpClient.send(request, BodyHandlers.ofByteArray());
                Map<String, String> dto = jsonParser.readValue(response.body(), new TypeReference<>(){});
                cache = dtoMapper.map(dto);

                stopWatch.stop();
                String infoMessage = "Fetched and cached %d currencies in %dms"
                        .formatted(cache.size(), stopWatch.getTime(TimeUnit.MILLISECONDS));
                LOG.info(infoMessage);
            } catch (IOException | InterruptedException e) {
                LOG.error("Failed to initialize currency data: " + e);
                throw e;
            }
        }
    }
}
