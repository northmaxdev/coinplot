// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;

public final class Strings {

    private static final int INDEX_NOT_FOUND = -1;
    private static final Predicate<String> NOT_BLANK = Predicate.not(String::isBlank);
    private static final Predicate<String> NOT_EMPTY = Predicate.not(String::isEmpty);

    private Strings() {
        throw new UnsupportedOperationException();
    }

    public static OptionalInt indexOf(@Nonnull String haystack, @Nonnull String needle) {
        Objects.requireNonNull(haystack);
        Objects.requireNonNull(needle);

        int index = haystack.indexOf(needle);
        return index == INDEX_NOT_FOUND ? OptionalInt.empty() : OptionalInt.of(index);
    }

    public static Optional<String> substringBefore(@Nonnull String s, @Nonnull String delimiter) {
        OptionalInt delimiterIndex = indexOf(s, delimiter); // Implicit null-check

        if (delimiterIndex.isEmpty()) {
            return Optional.empty();
        }

        String substring = s.substring(0, delimiterIndex.getAsInt());
        return Optional.of(substring);
    }

    public static Optional<String> blankToOptional(@Nullable String s) {
        return Optional.ofNullable(s)
                .filter(NOT_BLANK);
    }

    public static Optional<String> emptyToOptional(@Nullable String s) {
        return Optional.ofNullable(s)
                .filter(NOT_EMPTY);
    }
}
