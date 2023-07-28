// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.currency.AbstractCurrencyService;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyRepository;
import io.github.northmaxdev.coinplot.backend.core.web.RequiresAuthentication;
import io.github.northmaxdev.coinplot.lang.Strings;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Optional;

@Service
public final class EverapiCurrencyService
        extends AbstractCurrencyService<EverapiCurrencySetRequest, EverapiCurrencySetDTO>
        implements RequiresAuthentication {

    private @Nullable String accessKey;

    @Autowired
    public EverapiCurrencyService(
            HttpClient httpClient,
            ObjectMapper jsonParser,
            CurrencyRepository repository) {
        super(httpClient, jsonParser, new EverapiCurrencySetDTOMapper(), repository);
        this.accessKey = null;
    }

    public void setAccessKey(@Nullable String accessKey) {
        this.accessKey = Strings.blankToNull(accessKey);
    }

    @Override
    public boolean canAuthenticate() {
        return accessKey != null;
    }

    @Override
    protected Optional<EverapiCurrencySetRequest> createAPIRequest() {
        return Optional.ofNullable(accessKey)
                .map(EverapiCurrencySetRequest::new);
    }

    @Override
    protected EverapiCurrencySetDTO parseResponseBody(byte[] responseBody, ObjectMapper jsonParser) throws IOException {
        return jsonParser.readValue(responseBody, EverapiCurrencySetDTO.class);
    }
}
