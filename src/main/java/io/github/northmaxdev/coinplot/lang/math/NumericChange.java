// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;

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
}
