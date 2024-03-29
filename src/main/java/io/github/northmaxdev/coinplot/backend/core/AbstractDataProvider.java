// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core;

import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateService;
import jakarta.annotation.Nonnull;

import java.util.Objects;

public abstract class AbstractDataProvider implements DataProvider {

    private final @Nonnull CurrencyService currencyService;
    private final @Nonnull ExchangeRateService exchangeRateService;

    protected AbstractDataProvider(@Nonnull CurrencyService currencyService, @Nonnull ExchangeRateService exchangeRateService) {
        this.currencyService = Objects.requireNonNull(currencyService);
        this.exchangeRateService = Objects.requireNonNull(exchangeRateService);
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
    public final @Nonnull String toString() {
        return getDisplayName();
    }
}
