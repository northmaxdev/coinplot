// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;

import java.math.BigDecimal;

public final class BigDecimals {

    public static final BigDecimal HUNDRED = BigDecimal.valueOf(100L);

    private BigDecimals() {
        throw new UnsupportedOperationException();
    }

    public static boolean equalIgnoringScale(@Nonnull BigDecimal x, @Nonnull BigDecimal y) {
        return x.compareTo(y) == 0;
    }

    public static boolean equalsZeroIgnoringScale(@Nonnull BigDecimal x) {
        return equalIgnoringScale(x, BigDecimal.ZERO);
    }

    public static int hashIgnoringScale(@Nonnull BigDecimal x) {
        // Implementation idea from: https://jqno.nl/equalsverifier/errormessages/bigdecimal-equality/
        BigDecimal normalized = x.stripTrailingZeros();
        return normalized.hashCode();
    }
}
