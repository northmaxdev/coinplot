// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

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
public final class FrankfurterExchangeRateService extends
        AbstractExchangeRateFetchService<FrankfurterExchangeRateSetRequest, FrankfurterExchangeRateSetDTO> {

    private final @Nonnull FrankfurterConfiguration config;

    @Autowired
    public FrankfurterExchangeRateService(
            @Nonnull HttpClient httpClient,
            @Nonnull ObjectMapper jsonParser,
            @Nonnull FrankfurterCurrencyService currencyService,
            @Nonnull ExchangeRateRepository repository,
            @Nonnull FrankfurterConfiguration config) {
        super(
                httpClient,
                jsonParser,
                JSONMapper.forClass(FrankfurterExchangeRateSetDTO.class),
                new FrankfurterExchangeRateSetDTOMapper(currencyService),
                repository
        );
        this.config = Objects.requireNonNull(config);
    }

    @Override
    protected @Nonnull FrankfurterExchangeRateSetRequest createAPIRequest(@Nonnull ExchangeBatch exchanges)
            throws CannotCreateAPIRequestException {
        // No need to null-check 'exchanges'
        return config.getCustomHost()
                .map(host -> new FrankfurterExchangeRateSetRequest(host, exchanges))
                .orElseGet(() -> new FrankfurterExchangeRateSetRequest(exchanges));
    }
}
