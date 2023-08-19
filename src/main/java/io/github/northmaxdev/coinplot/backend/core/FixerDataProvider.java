// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core;

import io.github.northmaxdev.coinplot.backend.fixer.FixerCurrencyService;
import io.github.northmaxdev.coinplot.backend.fixer.FixerExchangeRateService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class FixerDataProvider extends AbstractDataProvider {

    @Autowired
    public FixerDataProvider(
            @Nonnull FixerCurrencyService currencyService,
            @Nonnull FixerExchangeRateService exchangeRateService) {
        super(currencyService, exchangeRateService);
    }

    @Override
    public @Nonnull String getDisplayName() {
        return "fixer.io";
    }
}
