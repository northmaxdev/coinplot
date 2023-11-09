// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import io.github.northmaxdev.coinplot.backend.core.AbstractDataProvider;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
final class Fixer extends AbstractDataProvider { // Package-private

    @Autowired
    public Fixer(@Nonnull FixerCurrencyService currencyService, @Nonnull FixerExchangeRateService exchangeRateService) {
        super(currencyService, exchangeRateService);
    }

    @Override
    public @Nonnull String getDisplayName() {
        return "Fixer API";
    }

    @Override
    public @Nonnull String getID() {
        return "fixer";
    }
}
