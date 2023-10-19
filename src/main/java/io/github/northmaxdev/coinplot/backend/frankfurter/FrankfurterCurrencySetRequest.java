// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.backend.core.currency.CurrencySetRequest;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.hc.core5.http.HttpHost;

final class FrankfurterCurrencySetRequest extends AbstractFrankfurterAPIRequest implements CurrencySetRequest { // Package-private

    public FrankfurterCurrencySetRequest() {
        super();
    }

    public FrankfurterCurrencySetRequest(@Nullable HttpHost customHost) {
        super(customHost);
    }

    @Override
    protected @Nonnull String getEndpoint() {
        return "currencies";
    }
}
