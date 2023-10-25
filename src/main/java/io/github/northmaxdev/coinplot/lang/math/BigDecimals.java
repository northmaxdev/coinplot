// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;

import java.math.BigDecimal;

public final class BigDecimals {

    private BigDecimals() {
        throw new UnsupportedOperationException();
    }

    // An "equalConsideringScale(x, y)" method can also be added if needed.
    // Reference implementation: x.equals(y); (see BigDecimal::equals for more info).

    public static boolean equalIgnoringScale(@Nonnull BigDecimal x, @Nonnull BigDecimal y) {
        return x.compareTo(y) == 0;
    }
}
