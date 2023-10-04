// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.backend.core.AbstractDataProvider;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class Frankfurter extends AbstractDataProvider {

    // The class is public, but the constructor is package-private.
    // This lets us inject, but not instantiate manually.
    @Autowired
    Frankfurter(@Nonnull FrankfurterCurrencyService currencyService, @Nonnull FrankfurterExchangeRateService exchangeRateService) {
        super(currencyService, exchangeRateService);
    }

    @Override
    public @Nonnull String getDisplayName() {
        return "Frankfurter API";
    }

    @Override
    public @Nonnull String getID() {
        return "frankfurter";
    }
}
