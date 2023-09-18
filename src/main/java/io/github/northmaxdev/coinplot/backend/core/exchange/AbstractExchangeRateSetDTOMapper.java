// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Objects;
import java.util.Optional;

public abstract class AbstractExchangeRateSetDTOMapper<D> implements ExchangeRateSetDTOMapper<D> {

    private final @Nonnull CurrencyService currencyDataSource;

    protected AbstractExchangeRateSetDTOMapper(@Nonnull CurrencyService currencyDataSource) {
        this.currencyDataSource = Objects.requireNonNull(currencyDataSource);
    }

    protected final Optional<Currency> getCurrency(@Nullable String code) {
        return currencyDataSource.getCurrency(code);
    }
}
