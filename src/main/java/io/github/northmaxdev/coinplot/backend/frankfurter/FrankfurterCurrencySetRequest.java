// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.backend.core.currency.CurrencySetRequest;
import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.NameValuePair;

import java.util.List;
import java.util.Objects;

public final class FrankfurterCurrencySetRequest extends AbstractFrankfurterAPIRequest implements CurrencySetRequest {

    public FrankfurterCurrencySetRequest() {
        super();
    }

    public FrankfurterCurrencySetRequest(@Nonnull HttpHost customHost) {
        super(customHost);
    }

    @Override
    protected @Nonnull String getEndpoint() {
        return "currencies";
    }

    @Override
    protected List<NameValuePair> getAdditionalParameters() {
        return List.of();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof FrankfurterCurrencySetRequest that
                // Host is the only state in this class
                && Objects.equals(this.getHost(), that.getHost());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getHost());
    }
}
