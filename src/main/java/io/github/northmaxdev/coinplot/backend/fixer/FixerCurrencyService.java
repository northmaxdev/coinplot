// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

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
public final class FixerCurrencyService
        extends AbstractCurrencyService<FixerCurrencySetRequest, FixerCurrencySetDTO>
        implements RequiresAuthentication {

    private @Nullable String accessKey;

    @Autowired
    public FixerCurrencyService(
            HttpClient httpClient,
            ObjectMapper jsonParser,
            CurrencyRepository repository) {
        super(httpClient, jsonParser, new FixerCurrencySetDTOMapper(), repository);
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
    protected Optional<FixerCurrencySetRequest> createAPIRequest() {
        return Optional.ofNullable(accessKey)
                .map(FixerCurrencySetRequest::new);
    }

    @Override
    protected FixerCurrencySetDTO parseResponseBody(byte[] responseBody, ObjectMapper jsonParser) throws IOException {
        return jsonParser.readValue(responseBody, FixerCurrencySetDTO.class);
    }
}
