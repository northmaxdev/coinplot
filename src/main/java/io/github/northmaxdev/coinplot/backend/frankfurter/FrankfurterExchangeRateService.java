// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

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
final class FrankfurterExchangeRateService extends // Package-private
        AbstractExchangeRateFetchService<FrankfurterExchangeRateSetRequest, CommonExchangeRateSetDTO> {

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
                JSONParsingStrategy.usingClass(CommonExchangeRateSetDTO.class),
                new CommonExchangeRateSetDTOMapper(currencyService),
                repository
        );
        this.config = Objects.requireNonNull(config);
    }

    @Override
    protected @Nonnull FrankfurterExchangeRateSetRequest createAPIRequest(@Nonnull ExchangeBatch exchangeBatch) {
        // config.customHost() might return null, but this constructor explicitly handles a null custom host
        return new FrankfurterExchangeRateSetRequest(config.customHost(), exchangeBatch);
    }
}
