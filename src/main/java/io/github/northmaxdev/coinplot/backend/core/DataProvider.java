// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core;

import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateService;
import jakarta.annotation.Nonnull;

public interface DataProvider {

    DataProvider NO_OP = new DataProvider() {

        @Override
        public @Nonnull CurrencyService getCurrencyService() {
            return CurrencyService.NO_OP;
        }

        @Override
        public @Nonnull ExchangeRateService getExchangeRateService() {
            return ExchangeRateService.NO_OP;
        }

        @Override
        public @Nonnull String getDisplayName() {
            return "No-op";
        }

        @Override
        public @Nonnull String getID() {
            return "noop";
        }
    };

    @Nonnull CurrencyService getCurrencyService();

    @Nonnull ExchangeRateService getExchangeRateService();

    // A "frontal" display name of this DataProvider (usually the full name of a brand, company or organization).
    // Example: "European Central Bank"
    @Nonnull String getDisplayName();

    // An ID intended only for internal usage throughout this project (where relevant).
    // Example: "ecb"
    @Nonnull String getID();
}
