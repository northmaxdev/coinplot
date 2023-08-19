// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

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
public final class EverapiExchangeRateService extends
        AbstractExchangeRateFetchService<EverapiExchangeRateSetRequest, EverapiExchangeRateSetDTO> {

    private final @Nonnull EverapiConfiguration config;

    @Autowired
    public EverapiExchangeRateService(
            @Nonnull HttpClient httpClient,
            @Nonnull ObjectMapper jsonParser,
            @Nonnull EverapiCurrencyService currencyService,
            @Nonnull ExchangeRateRepository repository,
            @Nonnull EverapiConfiguration config) {
        super(httpClient, jsonParser, new EverapiExchangeRateSetDTOMapper(currencyService), repository);
        this.config = Objects.requireNonNull(config, "config is null");
    }

    @Override
    protected @Nonnull EverapiExchangeRateSetRequest createAPIRequest(
            @Nullable Currency base,
            Set<Currency> targets,
            @Nonnull LocalDateInterval dateInterval) throws CannotCreateAPIRequestException {
        return config.getAccessKey()
                .map(accessKeyValue -> new EverapiExchangeRateSetRequest(accessKeyValue, base, targets, dateInterval))
                .orElseThrow(CannotCreateAPIRequestException::forNoAccessKey);
    }

    @Override
    protected @Nonnull EverapiExchangeRateSetDTO parseResponseBody(
            byte[] responseBody,
            @Nonnull ObjectMapper jsonParser) throws IOException {
        return jsonParser.readValue(responseBody, EverapiExchangeRateSetDTO.class);
    }
}
