// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;

import java.util.Objects;
import java.util.Optional;
import java.util.SortedMap;

public final class Maps {

    private Maps() {
        throw new UnsupportedOperationException();
    }

    public static <K, V> Optional<V> getFirstValue(@Nonnull SortedMap<K, V> sortedMap) {
        Objects.requireNonNull(sortedMap);

        // SortedMap::firstKey throws NoSuchElementException if querying an empty map
        if (sortedMap.isEmpty()) {
            return Optional.empty();
        }

        // Nullability annotations are absent because it's up to the SortedMap implementation
        K key = sortedMap.firstKey();
        V value = sortedMap.get(key);
        return Optional.ofNullable(value);
    }

    public static <K, V> Optional<V> getLastValue(@Nonnull SortedMap<K, V> sortedMap) {
        Objects.requireNonNull(sortedMap);

        // SortedMap::lastKey throws NoSuchElementException if querying an empty map
        if (sortedMap.isEmpty()) {
            return Optional.empty();
        }

        // Nullability annotations are absent because it's up to the SortedMap implementation
        K key = sortedMap.lastKey();
        V value = sortedMap.get(key);
        return Optional.ofNullable(value);
    }
}
