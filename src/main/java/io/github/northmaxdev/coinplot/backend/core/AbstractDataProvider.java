// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core;

import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateService;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.net.URI;
import java.util.Optional;

public abstract class AbstractDataProvider implements DataProvider {

    private final @Nonnull CurrencyService currencyService;
    private final @Nonnull ExchangeRateService exchangeRateService;
    private final @Nullable URI frontPageURI;

    protected AbstractDataProvider(
            @Nonnull CurrencyService currencyService,
            @Nonnull ExchangeRateService exchangeRateService) {
        this(currencyService, exchangeRateService, null);
    }

    protected AbstractDataProvider(
            @Nonnull CurrencyService currencyService,
            @Nonnull ExchangeRateService exchangeRateService,
            @Nullable URI frontPageURI) {
        this.currencyService = currencyService;
        this.exchangeRateService = exchangeRateService;
        this.frontPageURI = frontPageURI;
    }

    @Override
    public final @Nonnull CurrencyService getCurrencyService() {
        return currencyService;
    }

    @Override
    public final @Nonnull ExchangeRateService getExchangeRateService() {
        return exchangeRateService;
    }

    @Override
    public final Optional<URI> getFrontPageURI() {
        return Optional.ofNullable(frontPageURI);
    }
}
