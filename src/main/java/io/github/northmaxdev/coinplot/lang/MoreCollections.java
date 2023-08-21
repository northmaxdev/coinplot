// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class MoreCollections {

    private MoreCollections() {
        throw new UnsupportedOperationException();
    }

    public static <T> @Nonnull Set<T> emptyIfNull(@Nullable Set<T> set) {
        return Objects.requireNonNullElseGet(set, Set::of);
    }

    public static <T> @Nonnull List<T> emptyIfNull(@Nullable T[] array) {
        return array == null ? List.of() : List.of(array);
    }
}
