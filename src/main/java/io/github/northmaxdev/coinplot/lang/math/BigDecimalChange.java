// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

record BigDecimalChange(@Nonnull BigDecimal a, @Nonnull BigDecimal b) implements NumericChange<BigDecimal> { // Package-private

    public BigDecimalChange(@Nonnull BigDecimal a, @Nonnull BigDecimal b) {
        this.a = Objects.requireNonNull(a);
        this.b = Objects.requireNonNull(b);
    }

    @Override
    public @Nonnull BigDecimal getInitialValue() {
        return a;
    }

    @Override
    public @Nonnull BigDecimal getResultingValue() {
        return b;
    }

    @Override
    public @Nonnull BigDecimal asDifference() {
        return b.subtract(a);
    }

    @Override
    public Optional<Percentage> asPercentage() {
        if (BigDecimals.equalsZeroIgnoringScale(a) && !BigDecimals.equalsZeroIgnoringScale(b)) {
            return Optional.empty();
        }

        Percentage percentage = Percentage.ofChange(a, b);
        return Optional.of(percentage);
    }
}
