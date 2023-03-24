// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.currency;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.config.APIConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

@Service
public final class CurrencyService {

    private static final Logger LOG = LoggerFactory.getLogger(CurrencyService.class);

    private final CurrencyRepository repository;
    private final APIConfig apiConfig;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final CurrencyDTOMapper dtoMapper;

    @Autowired
    public CurrencyService(CurrencyRepository repository,
                           APIConfig apiConfig,
                           HttpClient httpClient,
                           ObjectMapper objectMapper,
                           CurrencyDTOMapper dtoMapper) {
        this.repository = repository;
        this.apiConfig = apiConfig;
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.dtoMapper = dtoMapper;
    }

    public List<Currency> getAvailableCurrencies() {
        if (repository.isEmpty()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(apiConfig.createCurrenciesEndpointURI())
                    .GET()
                    .build();

            try {
                // TODO: Profile other response content types, maybe byte[] is faster?
                HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
                Map<String, String> dto = objectMapper.readValue(response.body(), new TypeReference<>(){});
                List<Currency> currencies = dtoMapper.map(dto);

                repository.saveAll(currencies);
                LOG.info("Initialized currency database with " + currencies.size() + " entries");
            } catch (IOException | InterruptedException e) {
                LOG.warn("Failed to initialize currency database: " + e);
            }
        }

        return repository.findAll();
    }
}
