// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.request.currencyapi;

import jakarta.annotation.Nonnull;

public final class CurrencyAPICurrenciesRequest extends AbstractCurrencyAPIRequest {

    @Override
    protected @Nonnull String getEndpoint() {
        return "currencies";
    }
}
