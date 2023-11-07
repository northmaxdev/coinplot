// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import io.github.northmaxdev.coinplot.lang.Pair;
import jakarta.annotation.Nonnull;

import java.math.BigDecimal;

public interface NumericChange<T extends Number> {

    @Nonnull T getPreValue();

    @Nonnull T getPostValue();

    @Nonnull T asDifference();

    @Nonnull Percentage asPercentage();

    default @Nonnull Pair<T, T> toPair() {
        return new Pair<>(getPreValue(), getPostValue());
    }

    static @Nonnull NumericChange<BigDecimal> of(@Nonnull BigDecimal pre, @Nonnull BigDecimal post) {
        return new BigDecimalChange(pre, post);
    }

    static @Nonnull NumericChange<Double> of(double pre, double post) {
        return new DoubleChange(pre, post);
    }

    static @Nonnull NumericChange<Long> of(long pre, long post) {
        return new LongChange(pre, post);
    }
}
