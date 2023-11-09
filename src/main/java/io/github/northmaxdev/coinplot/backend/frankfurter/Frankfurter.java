// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.backend.core.AbstractDataProvider;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
final class Frankfurter extends AbstractDataProvider { // Package-private

    @Autowired
    public Frankfurter(@Nonnull FrankfurterCurrencyService currencyService, @Nonnull FrankfurterExchangeRateService exchangeRateService) {
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
