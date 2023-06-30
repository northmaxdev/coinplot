// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.currency;

import io.github.northmaxdev.coinplot.backend.web.request.AbstractCurrencyAPIRequest;
import jakarta.annotation.Nonnull;

import java.util.List;

public final class CurrencyAPICurrenciesRequest extends AbstractCurrencyAPIRequest {

    private static final List<String> PATH_SEGMENTS = createPathSegments("currencies");

    public CurrencyAPICurrenciesRequest() {
        super();
    }

    @Override
    protected @Nonnull List<String> getPathSegments() {
        return PATH_SEGMENTS;
    }
}
