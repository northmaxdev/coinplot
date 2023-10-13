// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.util.Objects;

// Quick concept description: https://www.statista.com/statistics-glossary/definition/204/extreme_value/
// Primitive specializations (e.g., OptionalInt, OptionalLong and OptionalDouble) may be implemented if needed.
public record BigDecimalExtremesPair(@Nonnull BigDecimal min, @Nonnull BigDecimal max) {

    public BigDecimalExtremesPair {
        Objects.requireNonNull(min);
        Objects.requireNonNull(max);

        if (max.compareTo(min) < 0) {
            throw new IllegalArgumentException("max < min");
        }
    }

    public @Nonnull BigDecimal difference() {
        return max.subtract(min);
    }
}
