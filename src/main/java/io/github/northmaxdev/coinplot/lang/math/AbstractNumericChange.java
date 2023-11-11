// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Objects;

abstract class AbstractNumericChange<T extends Number> implements NumericChange<T> { // Package-private

    @Override
    public abstract boolean equals(@Nullable Object obj);

    @Override
    public int hashCode() { // Non-final (default implementation)
        return Objects.hash(getInitialValue(), getFinalValue());
    }

    @Override
    public final @Nonnull String toString() {
        return getInitialValue() + " → " + getFinalValue();
    }
}
