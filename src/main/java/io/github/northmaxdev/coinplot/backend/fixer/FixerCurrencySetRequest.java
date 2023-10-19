// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import io.github.northmaxdev.coinplot.backend.core.currency.CurrencySetRequest;
import jakarta.annotation.Nonnull;

final class FixerCurrencySetRequest extends AbstractFixerAPIRequest implements CurrencySetRequest { // Package-private

    public FixerCurrencySetRequest(@Nonnull String accessKey) {
        super(accessKey);
    }

    @Override
    protected @Nonnull String getEndpoint() {
        return "symbols";
    }
}
