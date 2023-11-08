// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;

import java.math.BigDecimal;

public interface NumericChange<T extends Number> {

    @Nonnull T getInitialValue();

    @Nonnull T getResultingValue();

    @Nonnull T asDifference();

    @Nonnull Percentage asPercentage();

    static @Nonnull NumericChange<BigDecimal> of(@Nonnull BigDecimal initialValue, @Nonnull BigDecimal resultingValue) {
        return new BigDecimalChange(initialValue, resultingValue);
    }

    static @Nonnull NumericChange<Double> of(double initialValue, double resultingValue) {
        return new DoubleChange(initialValue, resultingValue);
    }

    static @Nonnull NumericChange<Integer> of(int initialValue, int resultingValue) {
        return new IntChange(initialValue, resultingValue);
    }

    static @Nonnull NumericChange<Long> of(long initialValue, long resultingValue) {
        return new LongChange(initialValue, resultingValue);
    }
}
