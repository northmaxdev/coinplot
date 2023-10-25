// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;

import static java.util.stream.Collectors.toUnmodifiableSet;

public final class Maps {

    private Maps() {
        throw new UnsupportedOperationException();
    }

    public static <K, V, T> @Nonnull List<T> mapToList(@Nonnull Map<K, V> map, @Nonnull BiFunction<K, V, T> entryMapper) {
        Objects.requireNonNull(map);
        Objects.requireNonNull(entryMapper);
        return map.entrySet()
                .stream()
                .map(entry -> entryMapper.apply(entry.getKey(), entry.getValue()))
                .toList();
    }

    public static <K, V, T> @Nonnull Set<T> mapToSet(@Nonnull Map<K, V> map, @Nonnull BiFunction<K, V, T> entryMapper) {
        Objects.requireNonNull(map);
        Objects.requireNonNull(entryMapper);
        return map.entrySet()
                .stream()
                .map(entry -> entryMapper.apply(entry.getKey(), entry.getValue()))
                .collect(toUnmodifiableSet());
    }
}
