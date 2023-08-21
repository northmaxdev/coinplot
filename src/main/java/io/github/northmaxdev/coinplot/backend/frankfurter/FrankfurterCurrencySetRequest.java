// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.backend.core.currency.CurrencySetRequest;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.hc.core5.http.HttpHost;

import java.util.Objects;

public final class FrankfurterCurrencySetRequest extends AbstractFrankfurterAPIRequest implements CurrencySetRequest {

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

    @Override
    public boolean equals(Object obj) {
        return obj instanceof FrankfurterCurrencySetRequest that
                && Objects.equals(this.getAccessKey(), that.getAccessKey())
                && Objects.equals(this.getHost(), that.getHost());
    }
}
