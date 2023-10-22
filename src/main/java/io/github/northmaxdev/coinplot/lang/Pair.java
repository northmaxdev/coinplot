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

    // Alias
    public static <T> @Nonnull Pair<T> of(@Nonnull T first, @Nonnull T second) {
        return new Pair<>(first, second);
    }

    public @Nonnull Pair<T> flipped() {
        return new Pair<>(second, first);
    }
}
