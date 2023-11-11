// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.util.Objects;

final class BigDecimalChange extends AbstractNumericChange<BigDecimal> { // Package-private

    private final @Nonnull BigDecimal x1;
    private final @Nonnull BigDecimal x2;

    public BigDecimalChange(@Nonnull BigDecimal x1, @Nonnull BigDecimal x2) {
        this.x1 = Objects.requireNonNull(x1);
        this.x2 = Objects.requireNonNull(x2);
    }

    @Override
    public @Nonnull BigDecimal getInitialValue() {
        return x1;
    }

    @Override
    public @Nonnull BigDecimal getFinalValue() {
        return x2;
    }

    @Override
    public @Nonnull BigDecimal getDifference() {
        return x2.subtract(x1);
    }

    @Override
    public @Nonnull Percentage getPercentage() {
        if (isPercentageCalculable()) {
            return Percentage.ofChange(x1, x2);
        } else {
            throw new IllegalStateException("Change percentage is incalculable");
        }
    }

    @Override
    public boolean isPercentageIncalculable() {
        return BigDecimals.equalsZeroIgnoringScale(x1) && !BigDecimals.equalsZeroIgnoringScale(x2);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof BigDecimalChange that
                && BigDecimals.equalIgnoringScale(this.x1, that.x1)
                && BigDecimals.equalIgnoringScale(this.x2, that.x2);
    }

    @Override
    public int hashCode() {
        return BigDecimals.hashIgnoringScale(x1) ^ BigDecimals.hashIgnoringScale(x2);
    }
}
