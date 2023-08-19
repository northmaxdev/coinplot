// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import io.github.northmaxdev.coinplot.backend.core.currency.CurrencySetRequest;
import jakarta.annotation.Nonnull;

import java.util.Objects;

public final class EverapiCurrencySetRequest extends AbstractEverapiAPIRequest implements CurrencySetRequest {

    public EverapiCurrencySetRequest(@Nonnull String accessKeyValue) {
        super(accessKeyValue);
    }

    @Override
    protected @Nonnull String getEndpoint() {
        return "currencies";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EverapiCurrencySetRequest that
                && Objects.equals(this.getAccessKey(), that.getAccessKey());
    }
}
