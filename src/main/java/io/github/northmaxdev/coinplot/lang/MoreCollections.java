// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Objects;
import java.util.Set;

public final class MoreCollections {

    private MoreCollections() {
        throw new UnsupportedOperationException();
    }

    public static <T> @Nonnull Set<T> emptyIfNull(@Nullable Set<T> set) {
        return Objects.requireNonNullElseGet(set, Set::of);
    }
}
