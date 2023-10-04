// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core;

import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateService;
import io.github.northmaxdev.coinplot.lang.TextuallyDisplayable;
import jakarta.annotation.Nonnull;

public interface DataProvider extends TextuallyDisplayable {

    @Nonnull CurrencyService getCurrencyService();

    @Nonnull ExchangeRateService getExchangeRateService();

    // A "frontal" display name of this DataProvider (usually the full name of a brand, company or organization).
    // Example: "European Central Bank"
    @Override
    @Nonnull String getDisplayName();

    // An ID intended only for internal usage throughout this project (where relevant).
    // Example: "ecb"
    @Nonnull String getID();

    // TODO (Feature):
    //  1. Provider URI (getURI)
    //  2. Provider author display name (getAuthorDisplayName or getDeveloperDisplayName)
}
