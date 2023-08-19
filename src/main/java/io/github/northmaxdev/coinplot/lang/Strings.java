// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;

public final class Strings {

    private static final Predicate<String> NOT_BLANK = Predicate.not(String::isBlank);

    private Strings() {
        throw new UnsupportedOperationException();
    }

    // This is merely a wrapper for String::indexOf to work with OptionalInt instances instead of magic constants like
    // -1. It does not add any additional behavior to String::indexOf, therefore, one should refer primarily to that
    // method's documentation.
    public static OptionalInt indexOf(@Nonnull String haystack, @Nonnull String needle) {
        int index = haystack.indexOf(needle);
        return index == -1 ? OptionalInt.empty() : OptionalInt.of(index);
    }

    public static Optional<String> substringBefore(@Nonnull String s, @Nonnull String delimiter) {
        return indexOf(s, delimiter)
                .stream()
                .mapToObj(i -> s.substring(0, i))
                .findFirst(); // No difference between findFirst and findAny because we'll always have at most 1 element
    }

    public static Optional<String> blankToOptional(@Nullable String s) {
        return Optional.ofNullable(s)
                .filter(NOT_BLANK);
    }

    // The first argument is deliberately not annotated to not screw with static analysis tools
    public static @Nonnull String requireNonNullAndBlank(String s, @Nullable String message) {
        if (s == null) {
            throw new NullPointerException(message);
        }
        if (s.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return s;
    }
}
