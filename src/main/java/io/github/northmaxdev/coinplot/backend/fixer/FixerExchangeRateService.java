// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.northmaxdev.coinplot.backend.core.exchange.AbstractExchangeRateFetchService;
import io.github.northmaxdev.coinplot.backend.core.exchange.CommonExchangeRateSetDTO;
import io.github.northmaxdev.coinplot.backend.core.exchange.CommonExchangeRateSetDTOMapper;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeBatch;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateRepository;
import io.github.northmaxdev.coinplot.backend.core.web.response.JSONParsingStrategy;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.util.Objects;

@Service
final class FixerExchangeRateService extends // Package-private
        AbstractExchangeRateFetchService<FixerExchangeRateSetRequest, CommonExchangeRateSetDTO> {

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
                JSONParsingStrategy.usingClass(CommonExchangeRateSetDTO.class),
                new CommonExchangeRateSetDTOMapper(currencyService),
                repository
        );
        this.config = Objects.requireNonNull(config);
    }

    @Override
    protected @Nonnull FixerExchangeRateSetRequest createAPIRequest(@Nonnull ExchangeBatch exchangeBatch) {
        String accessKey = config.forcefullyGetAccessKey();
        return new FixerExchangeRateSetRequest(accessKey, exchangeBatch);
    }
}
