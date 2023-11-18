// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.util.Objects;

public final class NumericExtremes {

    private final @Nonnull BigDecimal min;
    private final @Nonnull BigDecimal max;

    // We don't validate min and max against each other because
    // the order might depend on custom Comparator rules.
    public NumericExtremes(@Nonnull BigDecimal min, @Nonnull BigDecimal max) {
        this.min = Objects.requireNonNull(min);
        this.max = Objects.requireNonNull(max);
    }

    public @Nonnull BigDecimal getMin() {
        return min;
    }

    public @Nonnull BigDecimal getMax() {
        return max;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof NumericExtremes that
                && BigDecimals.equalIgnoringScale(this.min, that.min)
                && BigDecimals.equalIgnoringScale(this.max, that.max);
    }

    @Override
    public int hashCode() {
        return BigDecimals.hashIgnoringScale(min) ^ BigDecimals.hashIgnoringScale(max);
    }

    @Override
    public @Nonnull String toString() {
        return "[min=" + min + " max=" + max + ']';
    }
}
