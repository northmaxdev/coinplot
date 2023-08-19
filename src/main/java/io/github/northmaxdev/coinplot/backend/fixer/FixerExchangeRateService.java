// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.exchange.AbstractExchangeRateFetchService;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateRepository;
import io.github.northmaxdev.coinplot.backend.core.web.request.CannotCreateAPIRequestException;
import io.github.northmaxdev.coinplot.lang.chrono.LocalDateInterval;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Objects;
import java.util.Set;

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
        this.config = Objects.requireNonNull(config, "config is null");
    }

    @Override
    protected @Nonnull FixerExchangeRateSetRequest createAPIRequest(
            @Nullable Currency base,
            Set<Currency> targets,
            @Nonnull LocalDateInterval dateInterval) throws CannotCreateAPIRequestException {
        return config.getAccessKey()
                .map(accessKeyValue -> new FixerExchangeRateSetRequest(accessKeyValue, base, targets, dateInterval))
                .orElseThrow(CannotCreateAPIRequestException::forNoAccessKey);
    }

    @Override
    protected @Nonnull FixerExchangeRateSetDTO parseResponseBody(byte[] responseBody, @Nonnull ObjectMapper jsonParser) throws IOException {
        return jsonParser.readValue(responseBody, FixerExchangeRateSetDTO.class);
    }
}
