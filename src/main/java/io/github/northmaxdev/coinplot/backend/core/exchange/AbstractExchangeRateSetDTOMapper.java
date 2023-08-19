// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.FailedDataFetchException;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import io.github.northmaxdev.coinplot.backend.core.web.response.DTOMappingException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Objects;
import java.util.Optional;

public abstract class AbstractExchangeRateSetDTOMapper<D> implements ExchangeRateSetDTOMapper<D> {

    private final @Nonnull CurrencyService currencyDataSource;

    protected AbstractExchangeRateSetDTOMapper(@Nonnull CurrencyService currencyDataSource) {
        this.currencyDataSource = Objects.requireNonNull(currencyDataSource, "currencyDataSource is null");
    }

    // Utility method for subclasses. @Nullable on parameter is to merely mirror CurrencyService::getCurrency signature.
    protected final @Nonnull Currency getCurrencyOrThrowDME(@Nullable String code) throws DTOMappingException {
        try {
            Optional<Currency> result = currencyDataSource.getCurrency(code);
            return result.orElseThrow(() -> new DTOMappingException("Currency by code \"" + code + "\" was not found"));
        } catch (FailedDataFetchException e) {
            throw new DTOMappingException("Failed to retrieve currency data (code: " + code + ')', e);
        }
    }
}
