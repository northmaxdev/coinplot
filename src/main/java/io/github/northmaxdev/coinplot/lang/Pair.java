// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;

import java.util.Objects;

// It is strongly recommended for 'T' to have proper equals/hashCode
// semantics for Pair<T> to function correctly.
public record Pair<T>(@Nonnull T first, @Nonnull T second) {

    public Pair(@Nonnull T first, @Nonnull T second) {
        this.first = Objects.requireNonNull(first);
        this.second = Objects.requireNonNull(second);
    }

    public static <T> @Nonnull Pair<T> mirrored(@Nonnull T value) {
        return new Pair<>(value, value);
    }

    public boolean isMirrored() {
        return first.equals(second);
    }

    public @Nonnull Pair<T> flipped() {
        return new Pair<>(second, first);
    }
}
