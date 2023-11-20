// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class Iterables {

    private Iterables() {
        throw new UnsupportedOperationException();
    }

    // All methods are implemented using switch expressions and pattern-matching
    // to allow for future extensibility of edge cases.

    public static long size(@Nonnull Iterable<?> iterable) {
        return switch (iterable) {
            case null -> throw new NullPointerException();
            case Collection<?> collection -> collection.size();
            default -> {
                int counter = 0;
                for (Object ignored : iterable) counter++;
                yield counter;
            }
        };
    }

    public static boolean isEmpty(@Nonnull Iterable<?> iterable) {
        return switch (iterable) {
            case null -> throw new NullPointerException();
            case Collection<?> collection -> collection.isEmpty();
            default -> {
                // Keep in mind that one can only query whether an iterator has any elements *remaining*
                Iterator<?> iterator = iterable.iterator();
                yield iterator.hasNext();
            }
        };
    }

    public static <T> @Nonnull Stream<T> stream(@Nonnull Iterable<T> iterable) {
        return stream(iterable, false);
    }

    public static <T> @Nonnull Stream<T> stream(@Nonnull Iterable<T> iterable, boolean parallel) {
        return switch (iterable) {
            case null -> throw new NullPointerException();
            case Collection<T> collection -> parallel ? collection.parallelStream() : collection.stream();
            default -> StreamSupport.stream(iterable.spliterator(), parallel);
        };
    }

    public static <T> @Nonnull Stream<T> parallelStream(@Nonnull Iterable<T> iterable) {
        return stream(iterable, true);
    }
}
