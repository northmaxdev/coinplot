// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;

import java.util.Optional;

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
    public Optional<Percentage> asPercentage() {
        if (a == 0L && b != 0L) {
            return Optional.empty();
        }

        Percentage percentage = Percentage.ofChange(a, b);
        return Optional.of(percentage);
    }
}
