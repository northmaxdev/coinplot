// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.currency.AbstractCurrencyFetchService;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyRepository;
import io.github.northmaxdev.coinplot.backend.core.web.request.CannotCreateAPIRequestException;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Objects;

@Service
public final class FixerCurrencyService extends AbstractCurrencyFetchService<FixerCurrencySetRequest, FixerCurrencySetDTO> {

    private final @Nonnull FixerConfiguration config;

    @Autowired
    public FixerCurrencyService(
            @Nonnull HttpClient httpClient,
            @Nonnull ObjectMapper jsonParser,
            @Nonnull CurrencyRepository repository,
            @Nonnull FixerConfiguration config) {
        super(httpClient, jsonParser, new FixerCurrencySetDTOMapper(), repository);
        this.config = Objects.requireNonNull(config);
    }

    @Override
    protected @Nonnull FixerCurrencySetRequest createAPIRequest() throws CannotCreateAPIRequestException {
        return config.getAccessKey()
                .map(FixerCurrencySetRequest::new)
                .orElseThrow(CannotCreateAPIRequestException::forNoAccessKey);
    }

    @Override
    protected @Nonnull FixerCurrencySetDTO parseResponseBody(
            @Nonnull byte[] responseBody,
            @Nonnull ObjectMapper jsonParser) throws IOException {
        return jsonParser.readValue(responseBody, FixerCurrencySetDTO.class);
    }
}
