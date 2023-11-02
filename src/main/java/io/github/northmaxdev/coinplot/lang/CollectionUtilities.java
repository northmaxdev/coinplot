// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.SequencedCollection;

// The "Utilities" suffix is used because the name
// "Collections" is *effectively* taken by the JDK.
public final class CollectionUtilities {

    private CollectionUtilities() {
        throw new UnsupportedOperationException();
    }

    // SequencedCollection specifies a contract that is semantically
    // much more appropriate for this method than a regular Collection.
    // The notions of "first" and "last" elements imply a well-defined
    // encounter order, which a regular Collection does not guarantee.
    //
    // The possibly resulting Pair<T> is mapped to the collection's last
    // two elements in the following way:
    // The pair's first item --> the collection's next-to-last element
    // The pair's second item --> the collection's last element
    // Visualization: [1, 6, 2, 4, 9, 0, 4, 7] --> [4, 7]
    //                                  ^~~~~~^
    // An empty Optional is returned if the collection has less than two elements.
    // This method does NOT take into account concurrent mutations of the collection.
    public static <T> Optional<Pair<T>> lastTwoElements(@Nonnull SequencedCollection<T> collection) {
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

        Pair<T> result = new Pair<>(nextToLastElement, lastElement);
        return Optional.of(result);
    }

    // See comments of CollectionUtilities::lastTwoElements for info about method and/or interface
    // contract stuff that is relevant to this method as well.
    //
    // Returns the first and the last elements (if present) of the collection.
    // Visualization: [1, 3, 5, 6, 8] --> [1, 8]
    //                 ^           ^
    // If the collection contains exactly one element, it is used as both the first
    // and the second values of the resulting Pair<T>.
    //
    // An empty Optional is returned if the collection is empty.
    // This method does NOT take into account concurrent mutations of the collection.
    public static <T> Optional<Pair<T>> endpoints(@Nonnull SequencedCollection<T> collection) {
        Objects.requireNonNull(collection);
        return switch (collection.size()) {
            case 0 -> Optional.empty();
            case 1 -> {
                Pair<T> p = Pair.mirrored(collection.getFirst());
                yield Optional.of(p);
            }
            default -> {
                Pair<T> p = new Pair<>(collection.getFirst(), collection.getLast());
                yield Optional.of(p);
            }
        };
    }

    // Exception-less alternative to SequencedCollection::getFirst.
    // This method does NOT take into account concurrent mutations of the collection.
    public static <T> Optional<T> firstElement(@Nonnull SequencedCollection<T> collection) {
        return collection.isEmpty() ? Optional.empty() : Optional.ofNullable(collection.getFirst());
    }

    // Exception-less alternative to SequencedCollection::getLast.
    // This method does NOT take into account concurrent mutations of the collection.
    public static <T> Optional<T> lastElement(@Nonnull SequencedCollection<T> collection) {
        return collection.isEmpty() ? Optional.empty() : Optional.ofNullable(collection.getLast());
    }
}
