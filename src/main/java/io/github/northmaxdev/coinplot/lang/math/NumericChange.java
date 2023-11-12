// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import io.github.northmaxdev.coinplot.lang.Doubles;
import jakarta.annotation.Nonnull;

import java.math.BigDecimal;

public interface NumericChange<T extends Number> {

    @Nonnull T getInitialValue();

    @Nonnull T getFinalValue();

    @Nonnull T getDifference();

    // Throws IllegalStateException if incalculable
    @Nonnull Percentage getPercentage();

    // Cannot calculate percentage if the initial value is zero, but the final value is non-zero.
    // As this is an edge case, it'll generally be easier to implement the "incalculable" method,
    // therefore, the "calculable" one is the one that gets a default implementation.

    boolean isPercentageIncalculable();

    default boolean isPercentageCalculable() {
        return !isPercentageIncalculable();
    }

    // Force implementations to provide a human-readable
    // and/or UI-suitable textual representation
    @Override
    @Nonnull String toString();

    static @Nonnull NumericChange<BigDecimal> of(@Nonnull BigDecimal x1, @Nonnull BigDecimal x2) {
        return BigDecimals.equalIgnoringScale(x1, x2) ? new BigDecimalConstancy(x1) : new BigDecimalChange(x1, x2);
    }

    static @Nonnull NumericChange<Double> of(double x1, double x2) {
        return Doubles.equals(x1, x2) ? new DoubleConstancy(x1) : new DoubleChange(x1, x2);
    }

    static @Nonnull NumericChange<Integer> of(int x1, int x2) {
        return x1 == x2 ? new IntConstancy(x1) : new IntChange(x1, x2);
    }

    static @Nonnull NumericChange<Long> of(long x1, long x2) {
        return x1 == x2 ? new LongConstancy(x1) : new LongChange(x1, x2);
    }
}
