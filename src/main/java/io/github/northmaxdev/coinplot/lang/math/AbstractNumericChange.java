// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

abstract class AbstractNumericChange<T extends Number> implements NumericChange<T> { // Package-private

    @Override
    public abstract boolean equals(@Nullable Object obj);

    @Override
    public abstract int hashCode();

    @Override
    public final @Nonnull String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getInitialValue());
        sb.append(" → ");
        sb.append(getFinalValue());

        sb.append(" (");
        sb.append(getDifference());
        if (isPercentageCalculable()) {
            sb.append(' ');
            sb.append(getPercentage());
        }
        sb.append(')');

        return sb.toString();
    }
}
