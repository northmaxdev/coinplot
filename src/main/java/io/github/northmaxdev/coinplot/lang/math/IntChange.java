// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;

record IntChange(int a, int b) implements NumericChange<Integer> { // Package-private

    @Override
    public @Nonnull Integer getInitialValue() {
        return a;
    }

    @Override
    public @Nonnull Integer getResultingValue() {
        return b;
    }

    @Override
    public @Nonnull Integer asDifference() {
        return b - a;
    }

    @Override
    public @Nonnull Percentage asPercentage() {
        return Percentage.ofChange(a, b);
    }
}
