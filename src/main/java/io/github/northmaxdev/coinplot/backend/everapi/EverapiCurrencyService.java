// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

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
public final class EverapiCurrencyService extends AbstractCurrencyFetchService<EverapiCurrencySetRequest, EverapiCurrencySetDTO> {

    private final EverapiConfiguration config;

    @Autowired
    public EverapiCurrencyService(
            @Nonnull HttpClient httpClient,
            @Nonnull ObjectMapper jsonParser,
            @Nonnull CurrencyRepository repository,
            @Nonnull EverapiConfiguration config) {
        super(httpClient, jsonParser, new EverapiCurrencySetDTOMapper(), repository);
        this.config = Objects.requireNonNull(config, "config is null");
    }

    @Override
    protected @Nonnull EverapiCurrencySetRequest createAPIRequest() throws CannotCreateAPIRequestException {
        return config.getAccessKey()
                .map(EverapiCurrencySetRequest::new)
                .orElseThrow(CannotCreateAPIRequestException::forNoAccessKey);
    }

    @Override
    protected @Nonnull EverapiCurrencySetDTO parseResponseBody(byte[] responseBody, @Nonnull ObjectMapper jsonParser) throws IOException {
        return jsonParser.readValue(responseBody, EverapiCurrencySetDTO.class);
    }
}
