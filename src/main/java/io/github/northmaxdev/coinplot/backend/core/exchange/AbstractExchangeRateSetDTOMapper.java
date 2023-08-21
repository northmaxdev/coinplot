// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.FailedDataFetchException;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import io.github.northmaxdev.coinplot.backend.core.web.response.DTOMappingException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Objects;

public abstract class AbstractExchangeRateSetDTOMapper<D> implements ExchangeRateSetDTOMapper<D> {

    private final @Nonnull CurrencyService currencyDataSource;

    protected AbstractExchangeRateSetDTOMapper(@Nonnull CurrencyService currencyDataSource) {
        this.currencyDataSource = Objects.requireNonNull(currencyDataSource);
    }

    // Utility method for subclasses. @Nullable on parameter is to merely mirror CurrencyService::getCurrency signature.
    protected final @Nonnull Currency tryGetCurrency(@Nullable String code) throws DTOMappingException {
        try {
            return currencyDataSource.getCurrency(code)
                    .orElseThrow(() -> new DTOMappingException("Unknown currency: " + code));
        } catch (FailedDataFetchException e) {
            throw new DTOMappingException("Failed to fetch currency data", e);
        }
    }
}
