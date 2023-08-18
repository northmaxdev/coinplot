// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core;

import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateService;
import jakarta.annotation.Nonnull;

import java.net.URI;
import java.util.Optional;

public interface DataProvider {

    @Nonnull CurrencyService getCurrencyService();

    @Nonnull ExchangeRateService getExchangeRateService();

    @Nonnull String getDisplayName();

    default Optional<URI> getFrontPageURI() {
        return Optional.empty();
    }
}
