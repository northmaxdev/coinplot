// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nullable;

import java.util.OptionalInt;

public final class Ints {

    private Ints() {
        throw new UnsupportedOperationException();
    }

    public static int zeroIfNull(@Nullable Integer i) {
        return i == null ? 0 : i;
    }

    public static OptionalInt optionalOfNullable(@Nullable Integer i) {
        return i == null ? OptionalInt.empty() : OptionalInt.of(i);
    }
}
