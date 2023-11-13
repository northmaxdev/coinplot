// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.util.Objects;

final class BigDecimalConstancy extends AbstractNumericConstancy<BigDecimal> { // Package-private

    private final @Nonnull BigDecimal x;

    public BigDecimalConstancy(@Nonnull BigDecimal x) {
        this.x = Objects.requireNonNull(x);
    }

    @Override
    public @Nonnull BigDecimal getInitialValue() {
        return x;
    }

    @Override
    public @Nonnull BigDecimal getFinalValue() {
        return x;
    }

    @Override
    public @Nonnull BigDecimal getDifference() {
        return BigDecimal.ZERO;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof BigDecimalConstancy that
                && BigDecimals.equalIgnoringScale(this.x, that.x);
    }

    @Override
    public int hashCode() {
        return BigDecimals.hashIgnoringScale(x);
    }
}
