// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.util.Optional;

// Represents a NumericChange between two equal values, which is, in fact, not a change, but a "constancy".
final class NumericConstancy implements NumericChange { // Package-private

    private final @Nonnull BigDecimal x;

    // The caller is responsible for null-safety
    // (this is OK because this class is package-private and the hierarchy is sealed).
    public NumericConstancy(@Nonnull BigDecimal x) {
        this.x = x;
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
    public @Nonnull Percentage getPercentage() {
        return Percentage.ZERO;
    }

    @Override
    public boolean isPercentageCalculable() {
        return true;
    }

    @Override
    public Optional<Percentage> getPercentageIfCalculable() {
        return Optional.of(Percentage.ZERO);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof NumericConstancy that
                && BigDecimals.equalIgnoringScale(this.x, that.x);
    }

    @Override
    public int hashCode() {
        return BigDecimals.hashIgnoringScale(x);
    }

    @Override
    public @Nonnull String toString() {
        return x + " (no change)";
    }
}
