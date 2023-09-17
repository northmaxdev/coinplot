// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.currency.AbstractCurrencyFetchService;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyRepository;
import io.github.northmaxdev.coinplot.backend.core.web.response.JSONMapper;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.util.Map;
import java.util.Objects;

@Service
public final class FrankfurterCurrencyService extends AbstractCurrencyFetchService<FrankfurterCurrencySetRequest, Map<String, String>> {

    private static final TypeReference<Map<String, String>> DTO_TYPE_REF = new TypeReference<>() {};

    private final @Nonnull FrankfurterConfiguration config;

    @Autowired
    public FrankfurterCurrencyService(
            @Nonnull HttpClient httpClient,
            @Nonnull ObjectMapper jsonParser,
            @Nonnull CurrencyRepository repository,
            @Nonnull FrankfurterConfiguration config) {
        super(httpClient, jsonParser, JSONMapper.forTypeReference(DTO_TYPE_REF), Currency::createSet, repository);
        this.config = Objects.requireNonNull(config);
    }

    @Override
    protected @Nonnull FrankfurterCurrencySetRequest createAPIRequest() {
        // Instead of throwing an exception, just fall back to the public instance
        return config.getCustomHost()
                .map(FrankfurterCurrencySetRequest::new)
                .orElseGet(FrankfurterCurrencySetRequest::new);
    }
}
