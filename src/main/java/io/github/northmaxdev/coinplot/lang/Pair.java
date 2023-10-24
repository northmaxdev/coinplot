// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;

import java.util.Objects;

// If 'T' has well-defined equals/hashCode, then so does Pair<T>
public record Pair<T>(@Nonnull T first, @Nonnull T second) {

    public Pair(@Nonnull T first, @Nonnull T second) {
        this.first = Objects.requireNonNull(first);
        this.second = Objects.requireNonNull(second);
    }

    public static <T> @Nonnull Pair<T> ofSingleton(@Nonnull T singleton) {
        return new Pair<>(singleton, singleton);
    }

    public @Nonnull Pair<T> flipped() {
        return new Pair<>(second, first);
    }
}
