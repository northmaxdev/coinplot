// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;

import java.util.Optional;

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
    public Optional<Percentage> asPercentage() {
        if (a == 0 && b != 0) {
            return Optional.empty();
        }

        Percentage percentage = Percentage.ofChange(a, b);
        return Optional.of(percentage);
    }
}
