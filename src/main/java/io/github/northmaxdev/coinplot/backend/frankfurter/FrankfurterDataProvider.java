// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.backend.core.AbstractDataProvider;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class FrankfurterDataProvider extends AbstractDataProvider {

    @Autowired
    public FrankfurterDataProvider(
            @Nonnull FrankfurterCurrencyService currencyService,
            @Nonnull FrankfurterExchangeRateService exchangeRateService) {
        super(currencyService, exchangeRateService);
    }

    @Override
    public @Nonnull String getDisplayName() {
        return "Frankfurter";
    }
}
