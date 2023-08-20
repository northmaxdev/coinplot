// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import io.github.northmaxdev.coinplot.backend.core.AbstractDataProvider;
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
