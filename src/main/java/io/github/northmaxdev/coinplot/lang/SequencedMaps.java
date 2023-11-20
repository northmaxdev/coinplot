// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.SequencedMap;

public final class SequencedMaps {

    private SequencedMaps() {
        throw new UnsupportedOperationException();
    }

    public static <K, V> Optional<Map.Entry<K, V>> firstEntry(@Nonnull SequencedMap<K, V> sequencedMap) {
        Objects.requireNonNull(sequencedMap);

        @Nullable Map.Entry<K, V> entry = sequencedMap.firstEntry();
        return Optional.ofNullable(entry);
    }

    public static <K> Optional<K> firstKey(@Nonnull SequencedMap<K, ?> sequencedMap) {
        return firstEntry(sequencedMap)
                .map(Map.Entry::getKey);
    }

    public static <V> Optional<V> firstValue(@Nonnull SequencedMap<?, V> sequencedMap) {
        return firstEntry(sequencedMap)
                .map(Map.Entry::getValue);
    }

    public static <K, V> Optional<Map.Entry<K, V>> lastEntry(@Nonnull SequencedMap<K, V> sequencedMap) {
        Objects.requireNonNull(sequencedMap);

        @Nullable Map.Entry<K, V> entry = sequencedMap.lastEntry();
        return Optional.ofNullable(entry);
    }

    public static <K> Optional<K> lastKey(@Nonnull SequencedMap<K, ?> sequencedMap) {
        return lastEntry(sequencedMap)
                .map(Map.Entry::getKey);
    }

    public static <V> Optional<V> lastValue(@Nonnull SequencedMap<?, V> sequencedMap) {
        return lastEntry(sequencedMap)
                .map(Map.Entry::getValue);
    }
}
