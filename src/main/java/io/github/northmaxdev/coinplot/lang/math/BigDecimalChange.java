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
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean isPercentageIncalculable() {
        return BigDecimals.equalsZeroIgnoringScale(x1) && !BigDecimals.equalsZeroIgnoringScale(x2);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        // Should BigDecimals::equalIgnoringScale be used here instead?
        return obj instanceof BigDecimalChange that
                && this.x1.equals(that.x1)
                && this.x2.equals(that.x2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x1, x2);
    }
}
