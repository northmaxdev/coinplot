// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.currency.AbstractCurrencyFetchService;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyRepository;
import io.github.northmaxdev.coinplot.backend.core.web.request.CannotFormAPIRequestException;
import io.github.northmaxdev.coinplot.backend.core.web.request.NoAccessKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;

@Service
public final class EverapiCurrencyService
        extends AbstractCurrencyFetchService<EverapiCurrencySetRequest, EverapiCurrencySetDTO> {

    private final EverapiConfiguration config;

    @Autowired
    public EverapiCurrencyService(
            HttpClient httpClient,
            ObjectMapper jsonParser,
            CurrencyRepository repository,
            EverapiConfiguration config) {
        super(httpClient, jsonParser, new EverapiCurrencySetDTOMapper(), repository);
        this.config = config;
    }

    @Override
    protected EverapiCurrencySetRequest createAPIRequest() throws CannotFormAPIRequestException {
        return config.getAccessKey()
                .map(EverapiCurrencySetRequest::new)
                .orElseThrow(NoAccessKeyException::new);
    }

    @Override
    protected EverapiCurrencySetDTO parseResponseBody(byte[] responseBody, ObjectMapper jsonParser) throws IOException {
        return jsonParser.readValue(responseBody, EverapiCurrencySetDTO.class);
    }
}
