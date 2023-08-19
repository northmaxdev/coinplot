// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core;

import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateService;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

// TODO:
//  Subclass: Frankfurter (do not forget to write tests for equals/hashCode)
//  Subclass: Fixer (do not forget to write tests for equals/hashCode)
//  Subclass: EverapiCurrencyAPI (do not forget to write tests for equals/hashCode)
public abstract class AbstractDataProvider implements DataProvider {

    private final @Nonnull CurrencyService currencyService;
    private final @Nonnull ExchangeRateService exchangeRateService;
    private final @Nullable URI frontPageURI;

    protected AbstractDataProvider(@Nonnull CurrencyService currencyService,
                                   @Nonnull ExchangeRateService exchangeRateService) {
        this(currencyService, exchangeRateService, null);
    }

    protected AbstractDataProvider(@Nonnull CurrencyService currencyService,
                                   @Nonnull ExchangeRateService exchangeRateService,
                                   @Nullable URI frontPageURI) {
        this.currencyService = Objects.requireNonNull(currencyService, "currencyService is null");
        this.exchangeRateService = Objects.requireNonNull(exchangeRateService, "exchangeRateService is null");
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

    @Override
    public boolean equals(Object obj) { // Non-final
        return obj instanceof AbstractDataProvider that
                && Objects.equals(this.currencyService, that.currencyService)
                && Objects.equals(this.exchangeRateService, that.exchangeRateService)
                && Objects.equals(this.frontPageURI, that.frontPageURI);
    }

    @Override
    public int hashCode() { // Non-final
        return Objects.hash(currencyService, exchangeRateService, frontPageURI);
    }

    @Override
    public final @Nonnull String toString() {
        return getDisplayName();
    }
}
