// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nullable;

public final class Doubles {

    private Doubles() {
        throw new UnsupportedOperationException();
    }

    public static boolean equals(double d1, double d2) {
        return Double.doubleToLongBits(d1) == Double.doubleToLongBits(d2);
    }

    public static double requireFinite(double d) {
        return requireFinite(d, "value must be finite");
    }

    public static double requireFinite(double d, @Nullable String message) {
        if (Double.isFinite(d)) {
            return d;
        } else {
            throw new IllegalArgumentException(message);
        }
    }
}
