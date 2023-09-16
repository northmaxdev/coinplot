// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Collection;
import java.util.Objects;

public final class Iterables {

    private Iterables() {
        throw new UnsupportedOperationException();
    }

    public static boolean isEmpty(@Nonnull Iterable<?> iterable) {
        Objects.requireNonNull(iterable);
        return iterable instanceof Collection<?> collection
                ? collection.isEmpty()
                : iterable.iterator().hasNext(); // Keep in mind that one can only query whether an iterator has any elements *remaining*
    }

    public static boolean isNullOrEmpty(@Nullable Iterable<?> iterable) {
        return iterable == null || isEmpty(iterable);
    }
}
