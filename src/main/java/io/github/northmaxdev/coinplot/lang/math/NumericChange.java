// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public sealed interface NumericChange permits NumericConstancy, RegularNumericChange {

    @Nonnull BigDecimal getInitialValue();

    @Nonnull BigDecimal getFinalValue();

    @Nonnull BigDecimal getDifference();

    // Throws IllegalStateException if incalculable
    @Nonnull Percentage getPercentage();

    // Cannot calculate percentage if the initial value is zero, but the final value is non-zero.
    boolean isPercentageCalculable();

    default Optional<Percentage> getPercentageIfCalculable() {
        return isPercentageCalculable() ? Optional.of(getPercentage()) : Optional.empty();
    }

    static @Nonnull NumericChange of(@Nonnull BigDecimal x1, @Nonnull BigDecimal x2) {
        Objects.requireNonNull(x1);
        Objects.requireNonNull(x2);

        return BigDecimals.equalIgnoringScale(x1, x2)
                ? new NumericConstancy(x1)
                : new RegularNumericChange(x1, x2);
    }
}
