// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import io.github.northmaxdev.coinplot.lang.Pair;
import jakarta.annotation.Nonnull;

public interface NumericChange<T extends Number> {

    @Nonnull T getPreValue();

    @Nonnull T getPostValue();

    @Nonnull T asDifference();

    @Nonnull Percentage asPercentage();

    default @Nonnull Pair<T, T> toPair() {
        return new Pair<>(getPreValue(), getPostValue());
    }
}
