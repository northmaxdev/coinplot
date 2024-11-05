// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.util;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

public final class Strings {

    private static final int INDEX_NOT_FOUND = -1;

    private Strings() {
        throw new UnsupportedOperationException();
    }

    public static OptionalInt indexOf(String haystack, String needle) {
        Objects.requireNonNull(haystack);
        Objects.requireNonNull(needle);

        int index = haystack.indexOf(needle);
        return index == INDEX_NOT_FOUND ? OptionalInt.empty() : OptionalInt.of(index);
    }

    public static Optional<String> substringBefore(String s, String delimiter) {
        OptionalInt delimiterIndex = indexOf(s, delimiter); // Implicit null-check

        if (delimiterIndex.isEmpty()) {
            return Optional.empty();
        }

        String substring = s.substring(0, delimiterIndex.getAsInt());
        return Optional.of(substring);
    }
}
