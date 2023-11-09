// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;

import java.util.Optional;

record DoubleChange(double a, double b) implements NumericChange<Double> { // Package-private

    public DoubleChange {
        if (!Double.isFinite(a) || !Double.isFinite(b)) {
            throw new IllegalArgumentException("Values must be finite");
        }
    }

    @Override
    public @Nonnull Double getInitialValue() {
        return a;
    }

    @Override
    public @Nonnull Double getResultingValue() {
        return b;
    }

    @Override
    public @Nonnull Double asDifference() {
        return b - a;
    }

    @Override
    public Optional<Percentage> asPercentage() {
        throw new UnsupportedOperationException("Can't be arsed");
    }
}
