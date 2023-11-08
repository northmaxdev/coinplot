// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;

public interface NumericChange<T extends Number> {

    @Nonnull T getInitialValue();

    @Nonnull T getResultingValue();

    @Nonnull T asDifference();

    @Nonnull Percentage asPercentage();

    static @Nonnull NumericChange<Integer> of(int initialValue, int resultingValue) {
        return new IntChange(initialValue, resultingValue);
    }

    static @Nonnull NumericChange<Long> of(long initialValue, long resultingValue) {
        return new LongChange(initialValue, resultingValue);
    }
}
