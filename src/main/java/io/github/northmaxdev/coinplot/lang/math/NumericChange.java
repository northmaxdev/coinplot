// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;

public interface NumericChange<T extends Number> {

    @Nonnull T getInitialValue();

    @Nonnull T getFinalValue();

    @Nonnull T getDifference();

    // Throws IllegalStateException if not computable
    @Nonnull Percentage getPercentage();

    // Cannot compute percentage if the initial value is zero,
    // but the final value is non-zero.
    boolean isPercentageComputable();
}
