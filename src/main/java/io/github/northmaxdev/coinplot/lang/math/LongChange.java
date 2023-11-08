// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;

record LongChange(long a, long b) implements NumericChange<Long> { // Package-private

    @Override
    public @Nonnull Long getInitialValue() {
        return a;
    }

    @Override
    public @Nonnull Long getResultingValue() {
        return b;
    }

    @Override
    public @Nonnull Long asDifference() {
        return b - a;
    }

    @Override
    public @Nonnull Percentage asPercentage() {
        return Percentage.ofChange(a, b);
    }
}
