// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Iterator;
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

    // The pair's first item --> the collection's next-to-last element
    // The pair's second item --> the collection's last element
    // Visualization: [1, 6, 2, 4, 9, 0, 4, 7] --> [4, 7]
    //                                   ^~~^
    // An empty Optional is returned if the collection has less than two elements.
    public static <T> Optional<Pair<T, T>> lastTwoElements(@Nonnull SequencedCollection<T> collection) {
        Objects.requireNonNull(collection);

        @Nullable T lastElement = null;
        @Nullable T nextToLastElement = null;
        SequencedCollection<T> reversedView = collection.reversed();
        Iterator<T> reverseIterator = reversedView.iterator();

        if (reverseIterator.hasNext()) {
            lastElement = reverseIterator.next();
        }
        if (reverseIterator.hasNext()) {
            nextToLastElement = reverseIterator.next();
        }

        // If either of them is null, then the collection has less than two items.
        if (lastElement == null || nextToLastElement == null) {
            return Optional.empty();
        }

        Pair<T, T> result = new Pair<>(nextToLastElement, lastElement);
        return Optional.of(result);
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
