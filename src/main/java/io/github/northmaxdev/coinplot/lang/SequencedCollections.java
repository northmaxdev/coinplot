// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.SequencedCollection;
import java.util.function.BiFunction;

public final class SequencedCollections {

    private SequencedCollections() {
        throw new UnsupportedOperationException();
    }

    // IMPORTANT:
    // All utility methods in this class do NOT take into account
    // concurrent mutations of the provided collection(s).

    // Returns the last two elements (must not be null) if they exist.
    // Examples:
    //     []           -> none
    //     [5]          -> none
    //     [5, 4]       -> {5, 4}
    //     [5, 4, 8]    -> {4, 8}
    //     [5, 4, 8, 0] -> {8, 0}
    public static <T> Optional<Pair<T, T>> lastTwoElements(@Nonnull SequencedCollection<T> collection) {
        return applyToLastTwo(collection, Pair::new);
    }

    // Applies the given function (if possible) to the before-last and last elements, respectively.
    // It's OK for the function to produce nulls.
    // The function should cover null arguments if the collection permits null elements.
    // Examples:
    //     []           -> function is not applied
    //     [5]          -> function is not applied
    //     [5, 4]       -> f(5, 4)
    //     [5, 4, 8]    -> f(4, 8)
    //     [5, 4, 8, 0] -> f(8, 0)
    public static <E, R> Optional<R> applyToLastTwo(@Nonnull SequencedCollection<E> collection, @Nonnull BiFunction<E, E, R> function) {
        Objects.requireNonNull(collection);
        Objects.requireNonNull(function);

        if (collection.size() < 2) {
            return Optional.empty();
        }

        // Given: [5, 6, 1, 0, 4]
        SequencedCollection<E> reversedSlice = collection.reversed() // [4, 0, 1, 6, 5]
                .stream()
                .limit(2L) // [4, 0]
                .toList();

        @Nullable R result = function.apply(reversedSlice.getLast(), reversedSlice.getFirst());
        return Optional.ofNullable(result);
    }

    // Returns the first and last elements (must not be null) if they exist.
    // Examples:
    //     []           -> none
    //     [5]          -> {5, 5}
    //     [5, 4]       -> {5, 4}
    //     [5, 4, 8]    -> {5, 8}
    //     [5, 4, 8, 0] -> {5, 0}
    public static <T> Optional<Pair<T, T>> endpoints(@Nonnull SequencedCollection<T> collection) {
        return applyToEndpoints(collection, Pair::new);
    }

    // Applies the given function (if possible) to the first and last elements, respectively.
    // It's OK for the function to produce nulls.
    // The function should cover null arguments if the collection permits null elements.
    // Examples:
    //     []           -> function is not applied
    //     [5]          -> f(5, 5)
    //     [5, 4]       -> f(5, 4)
    //     [5, 4, 8]    -> f(5, 8)
    //     [5, 4, 8, 0] -> f(5, 0)
    public static <E, R> Optional<R> applyToEndpoints(@Nonnull SequencedCollection<E> collection, @Nonnull BiFunction<E, E, R> function) {
        Objects.requireNonNull(collection);
        Objects.requireNonNull(function);

        if (collection.isEmpty()) {
            return Optional.empty();
        }

        @Nullable R result = function.apply(collection.getFirst(), collection.getLast());
        return Optional.ofNullable(result);
    }

    // Exception-less alternative to SequencedCollection::getFirst.
    public static <T> Optional<T> firstElement(@Nonnull SequencedCollection<T> collection) {
        return collection.isEmpty() ? Optional.empty() : Optional.ofNullable(collection.getFirst());
    }

    // Exception-less alternative to SequencedCollection::getLast.
    public static <T> Optional<T> lastElement(@Nonnull SequencedCollection<T> collection) {
        return collection.isEmpty() ? Optional.empty() : Optional.ofNullable(collection.getLast());
    }
}
