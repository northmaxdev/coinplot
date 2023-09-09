// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.exchange.AbstractExchangeRateFetchService;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeBatch;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateRepository;
import io.github.northmaxdev.coinplot.backend.core.web.request.CannotCreateAPIRequestException;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Objects;

@Service
public final class FixerExchangeRateService extends
        AbstractExchangeRateFetchService<FixerExchangeRateSetRequest, FixerExchangeRateSetDTO> {

    private final @Nonnull FixerConfiguration config;

    @Autowired
    public FixerExchangeRateService(
            @Nonnull HttpClient httpClient,
            @Nonnull ObjectMapper jsonParser,
            @Nonnull FixerCurrencyService currencyService,
            @Nonnull ExchangeRateRepository repository,
            @Nonnull FixerConfiguration config) {
        super(httpClient, jsonParser, new FixerExchangeRateSetDTOMapper(currencyService), repository);
        this.config = Objects.requireNonNull(config);
    }

    @Override
    protected @Nonnull FixerExchangeRateSetRequest createAPIRequest(@Nonnull ExchangeBatch exchanges) throws CannotCreateAPIRequestException {
        return config.getAccessKey()
                .map(accessKeyValue -> new FixerExchangeRateSetRequest(accessKeyValue, exchanges))
                .orElseThrow(CannotCreateAPIRequestException::forNoAccessKey);
    }

    @Override
    protected @Nonnull FixerExchangeRateSetDTO parseResponseBody(
            @Nonnull byte[] responseBody,
            @Nonnull ObjectMapper jsonParser) throws IOException {
        return jsonParser.readValue(responseBody, FixerExchangeRateSetDTO.class);
    }
}
