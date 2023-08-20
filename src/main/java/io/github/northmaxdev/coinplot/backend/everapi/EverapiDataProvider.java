// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import io.github.northmaxdev.coinplot.backend.core.AbstractDataProvider;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class EverapiDataProvider extends AbstractDataProvider {

    @Autowired
    public EverapiDataProvider(
            @Nonnull EverapiCurrencyService currencyService,
            @Nonnull EverapiExchangeRateService exchangeRateService) {
        super(currencyService, exchangeRateService);
    }

    @Override
    public @Nonnull String getDisplayName() {
        return "currencyapi (Everapi GmbH)";
    }
}
