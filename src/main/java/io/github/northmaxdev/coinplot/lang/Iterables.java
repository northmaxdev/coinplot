// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Collection;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class Iterables {

    private Iterables() {
        throw new UnsupportedOperationException();
    }

    public static boolean isNullOrEmpty(@Nullable Iterable<?> iterable) {
        return switch (iterable) {
            case null -> true;
            case Collection<?> collection -> collection.isEmpty();
            // FIXME: Keep in mind that one can only query whether an iterator has any elements *remaining*
            default -> iterable.iterator().hasNext();
        };
    }

    public static <T> @Nonnull Stream<T> stream(@Nullable Iterable<T> iterable) {
        return stream(iterable, false);
    }

    public static <T> @Nonnull Stream<T> stream(@Nullable Iterable<T> iterable, boolean parallel) {
        return switch (iterable) {
            case null -> Stream.empty();
            case Collection<T> collection -> parallel ? collection.parallelStream() : collection.stream();
            default -> StreamSupport.stream(iterable.spliterator(), parallel);
        };
    }

    public static <T> @Nonnull Stream<T> parallelStream(@Nullable Iterable<T> iterable) {
        return stream(iterable, true);
    }
}
