// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

final class DoubleConstancy extends AbstractNumericConstancy<Double> { // Package-private

    private final double x;

    public DoubleConstancy(double x) {
        if (!Double.isFinite(x)) {
            throw new IllegalArgumentException("Value must be finite");
        }
        this.x = x;
    }

    @Override
    public @Nonnull Double getInitialValue() {
        return x;
    }

    @Override
    public @Nonnull Double getFinalValue() {
        return x;
    }

    @Override
    public @Nonnull Double getDifference() {
        return 0.0;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof DoubleConstancy that
                && Double.doubleToLongBits(this.x) == Double.doubleToLongBits(that.x);
    }

    @Override
    public int hashCode() {
        return Double.hashCode(x);
    }
}
