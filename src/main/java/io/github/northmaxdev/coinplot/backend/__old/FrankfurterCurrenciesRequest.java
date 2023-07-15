// SPDX-License-Identifier: MIT

// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.__old;

import io.github.northmaxdev.coinplot.backend.core.currency.CurrencySetRequest;
import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.HttpHost;

import java.util.List;
import java.util.Objects;

public final class FrankfurterCurrenciesRequest extends FrankfurterRequest implements CurrencySetRequest {

    private static final List<String> PATH_SEGMENTS = List.of("currencies");

    public FrankfurterCurrenciesRequest(@Nonnull HttpHost customHost) {
        super(customHost);
    }

    public FrankfurterCurrenciesRequest() {
        super();
    }

    @Override
    protected @Nonnull List<String> getPathSegments() {
        return PATH_SEGMENTS;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof FrankfurterCurrenciesRequest that
               && Objects.equals(this.getHost(), that.getHost());
    }

    @Override
    public int hashCode() {
        // TODO: Cache this
        return Objects.hashCode(getHost());
    }
}
