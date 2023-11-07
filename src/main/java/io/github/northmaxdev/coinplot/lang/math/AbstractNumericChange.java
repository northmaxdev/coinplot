// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Objects;

abstract sealed class AbstractNumericChange<T extends Number> // Package-private
        implements NumericChange<T>
        permits BigDecimalChange, DoubleChange, LongChange {

    private final @Nonnull T pre;
    private final @Nonnull T post;

    protected AbstractNumericChange(@Nonnull T pre, @Nonnull T post) {
        this.pre = Objects.requireNonNull(pre);
        this.post = Objects.requireNonNull(post);
    }

    @Override
    public final @Nonnull T getPreValue() {
        return pre;
    }

    @Override
    public final @Nonnull T getPostValue() {
        return post;
    }

    @Override
    public abstract boolean equals(@Nullable Object obj);

    @Override
    public final int hashCode() {
        return Objects.hash(pre, post);
    }

    @Override
    public final @Nonnull String toString() {
        return "[%s → %s]".formatted(pre, post);
    }
}
