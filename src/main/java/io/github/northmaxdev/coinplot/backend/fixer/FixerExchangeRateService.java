// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.exchange.AbstractExchangeRateFetchService;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeBatch;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateRepository;
import io.github.northmaxdev.coinplot.backend.core.web.request.CannotCreateAPIRequestException;
import io.github.northmaxdev.coinplot.backend.core.web.response.JSONMapper;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        super(
                httpClient,
                jsonParser,
                JSONMapper.forClass(FixerExchangeRateSetDTO.class),
                new FixerExchangeRateSetDTOMapper(currencyService),
                repository
        );
        this.config = Objects.requireNonNull(config);
    }

    @Override
    protected @Nonnull FixerExchangeRateSetRequest createAPIRequest(@Nonnull ExchangeBatch exchanges)
            throws CannotCreateAPIRequestException {
        // No need to null-check 'exchanges'
        return config.getAccessKey()
                .map(accessKeyValue -> new FixerExchangeRateSetRequest(accessKeyValue, exchanges))
                .orElseThrow(CannotCreateAPIRequestException::forNoAccessKey);
    }
}
