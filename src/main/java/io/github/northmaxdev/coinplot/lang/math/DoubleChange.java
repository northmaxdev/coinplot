// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import io.github.northmaxdev.coinplot.lang.Doubles;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

final class DoubleChange extends AbstractNumericChange<Double> { // Package-private

    private final double x1;
    private final double x2;

    public DoubleChange(double x1, double x2) {
        this.x1 = Doubles.requireFinite(x1);
        this.x2 = Doubles.requireFinite(x2);
    }

    @Override
    public @Nonnull Double getInitialValue() {
        return x1;
    }

    @Override
    public @Nonnull Double getFinalValue() {
        return x2;
    }

    @Override
    public @Nonnull Double getDifference() {
        return x2 - x1;
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
        throw new UnsupportedOperationException("Cannot be arsed");
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof DoubleChange that
                && Doubles.equals(this.x1, that.x1)
                && Doubles.equals(this.x2, that.x2);
    }

    @Override
    public int hashCode() {
        return Double.hashCode(x1) ^ Double.hashCode(x2);
    }
}
