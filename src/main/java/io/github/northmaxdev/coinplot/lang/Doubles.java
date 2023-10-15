// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nullable;

public final class Doubles { // Extra utilities for doubles

    private Doubles() {
        throw new UnsupportedOperationException();
    }

    public static double requireFinite(double d, @Nullable String message) { // This rejects NaN too
        if (Double.isFinite(d)) {
            return d;
        } else {
            throw new IllegalArgumentException(message);
        }
    }
}
