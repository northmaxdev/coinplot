// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

final class DoubleChange extends AbstractNumericChange<Double> { // Package-private

    private final double x1;
    private final double x2;

    public DoubleChange(double x1, double x2) {
        if (!Double.isFinite(x1) || !Double.isFinite(x2)) {
            throw new IllegalArgumentException("Values must be finite");
        }
        this.x1 = x1;
        this.x2 = x2;
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
                && Double.doubleToLongBits(this.x1) == Double.doubleToLongBits(that.x1)
                && Double.doubleToLongBits(this.x2) == Double.doubleToLongBits(that.x2);
    }

    // TODO:
    //  Consider overriding hashCode as follows:
    //  Double.hashCode(x1) ^ Double.hashCode(x2)
    //  Might be substantially more performant.
}
