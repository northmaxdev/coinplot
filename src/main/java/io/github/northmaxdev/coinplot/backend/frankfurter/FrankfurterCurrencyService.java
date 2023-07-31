// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.currency.AbstractCurrencyFetchService;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyRepository;
import io.github.northmaxdev.coinplot.backend.core.web.request.CannotFormAPIRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Map;

@Service
public final class FrankfurterCurrencyService
        extends AbstractCurrencyFetchService<FrankfurterCurrencySetRequest, Map<String, String>> {

    private static final TypeReference<Map<String, String>> DTO_TYPE_REF = new TypeReference<>() {};

    private final FrankfurterConfig config;

    @Autowired
    public FrankfurterCurrencyService(
            HttpClient httpClient,
            ObjectMapper jsonParser,
            CurrencyRepository repository,
            FrankfurterConfig config) {
        super(httpClient, jsonParser, new FrankfurterCurrencySetDTOMapper(), repository);
        this.config = config;
    }

    @Override
    protected FrankfurterCurrencySetRequest createAPIRequest() throws CannotFormAPIRequestException {
        // Instead of throwing a NoHostException, just fall back to the public instance.
        return config.getCustomHost()
                .map(FrankfurterCurrencySetRequest::new)
                .orElseGet(FrankfurterCurrencySetRequest::new);
    }

    @Override
    protected Map<String, String> parseResponseBody(byte[] responseBody, ObjectMapper jsonParser) throws IOException {
        return jsonParser.readValue(responseBody, DTO_TYPE_REF);
    }
}
