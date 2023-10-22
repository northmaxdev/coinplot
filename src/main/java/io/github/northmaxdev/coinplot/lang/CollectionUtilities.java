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
    // The pair's first item --> the collection's last element
    // The pair's second item --> the collection's next-to-last element
    //
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

        Pair<T> result = Pair.of(lastElement, nextToLastElement);
        return Optional.of(result);
    }
}
