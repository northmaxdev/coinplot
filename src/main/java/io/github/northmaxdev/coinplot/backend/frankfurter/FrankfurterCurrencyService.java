// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.currency.AbstractCurrencyService;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyRepository;
import jakarta.annotation.Nullable;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Map;
import java.util.Optional;

@Service
public final class FrankfurterCurrencyService
        extends AbstractCurrencyService<FrankfurterCurrencySetRequest, Map<String, String>> {

    private static final TypeReference<Map<String, String>> DTO_TYPE_REF = new TypeReference<>() {};

    private @Nullable HttpHost customHost;

    @Autowired
    public FrankfurterCurrencyService(
            HttpClient httpClient,
            ObjectMapper jsonParser,
            CurrencyRepository repository) {
        super(httpClient, jsonParser, new FrankfurterCurrencySetDTOMapper(), repository);
        this.customHost = null;
    }

    public void setCustomHost(@Nullable HttpHost customHost) {
        this.customHost = customHost;
    }

    public void setDefaultHost() {
        setCustomHost(null);
    }

    @Override
    protected Optional<FrankfurterCurrencySetRequest> createAPIRequest() {
        FrankfurterCurrencySetRequest request = FrankfurterCurrencySetRequest.forCustomOrElsePublicHost(customHost);
        return Optional.of(request);
    }

    @Override
    protected Map<String, String> parseResponseBody(byte[] responseBody, ObjectMapper jsonParser) throws IOException {
        return jsonParser.readValue(responseBody, DTO_TYPE_REF);
    }
}
