// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;

final class RegularNumericChange implements NumericChange { // Package-private

    private final @Nonnull BigDecimal x1;
    private final @Nonnull BigDecimal x2;

    // The caller is responsible for null-safety
    // (this is OK because this class is package-private and the hierarchy is sealed).
    public RegularNumericChange(@Nonnull BigDecimal x1, @Nonnull BigDecimal x2) {
        this.x1 = x1;
        this.x2 = x2;
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
    public boolean isPercentageCalculable() {
        boolean bad = BigDecimals.equalsZeroIgnoringScale(x1) && !BigDecimals.equalsZeroIgnoringScale(x2);
        return !bad;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof RegularNumericChange that
                && BigDecimals.equalIgnoringScale(this.x1, that.x1)
                && BigDecimals.equalIgnoringScale(this.x2, that.x2);
    }

    @Override
    public int hashCode() {
        return BigDecimals.hashIgnoringScale(x1) ^ BigDecimals.hashIgnoringScale(x2);
    }

    @Override
    public @Nonnull String toString() {
        return x1.toString() + '→' + x2;
    }
}
