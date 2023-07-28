// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;

import java.util.Optional;
import java.util.OptionalInt;

public final class Strings {

    private Strings() {}

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
}
