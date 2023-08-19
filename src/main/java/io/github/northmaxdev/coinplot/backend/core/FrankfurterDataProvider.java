// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core;

import io.github.northmaxdev.coinplot.backend.frankfurter.FrankfurterCurrencyService;
import io.github.northmaxdev.coinplot.backend.frankfurter.FrankfurterExchangeRateService;
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
