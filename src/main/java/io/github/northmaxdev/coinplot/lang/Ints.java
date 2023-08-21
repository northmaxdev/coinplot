// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nullable;

public final class Ints {

    private Ints() {
        throw new UnsupportedOperationException();
    }

    public static int zeroIfNull(@Nullable Integer i) {
        return i == null ? 0 : i;
    }
}
