// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import io.github.northmaxdev.coinplot.backend.core.currency.CurrencySetRequest;
import jakarta.annotation.Nonnull;

import java.util.Objects;

public final class FixerCurrencySetRequest extends AbstractFixerAPIRequest implements CurrencySetRequest {

    public FixerCurrencySetRequest(@Nonnull String accessKeyValue) {
        super(accessKeyValue);
    }

    @Override
    protected @Nonnull String getEndpoint() {
        return "symbols";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof FixerCurrencySetRequest that
                && Objects.equals(this.getAccessKey(), that.getAccessKey());
    }
}
