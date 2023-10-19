// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.currency.AbstractCurrencyFetchService;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyRepository;
import io.github.northmaxdev.coinplot.backend.core.web.response.JSONParsingStrategy;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.util.Objects;

@Service
final class FixerCurrencyService extends // Package-private
        AbstractCurrencyFetchService<FixerCurrencySetRequest, FixerCurrencySetDTO> {

    private final @Nonnull FixerConfiguration config;

    @Autowired
    public FixerCurrencyService(
            @Nonnull HttpClient httpClient,
            @Nonnull ObjectMapper jsonParser,
            @Nonnull CurrencyRepository repository,
            @Nonnull FixerConfiguration config) {
        super(
                httpClient,
                jsonParser,
                JSONParsingStrategy.usingClass(FixerCurrencySetDTO.class),
                new FixerCurrencySetDTOMapper(),
                repository
        );
        this.config = Objects.requireNonNull(config);
    }

    @Override
    protected @Nonnull FixerCurrencySetRequest createAPIRequest() {
        String accessKey = config.getAccessKey();
        return new FixerCurrencySetRequest(accessKey);
    }
}
