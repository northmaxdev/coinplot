// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.request.frankfurter;

import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.HttpHost;

import java.util.List;
import java.util.Objects;

public final class FrankfurterCurrenciesRequest extends AbstractFrankfurterRequest {

    public FrankfurterCurrenciesRequest(@Nonnull HttpHost customHost) {
        super(customHost);
    }

    public FrankfurterCurrenciesRequest() {
        super();
    }

    @Override
    protected @Nonnull List<String> getPathSegments() {
        return List.of("currencies");
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof FrankfurterCurrenciesRequest that
               && Objects.equals(this.getHost(), that.getHost());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getHost());
    }
}
