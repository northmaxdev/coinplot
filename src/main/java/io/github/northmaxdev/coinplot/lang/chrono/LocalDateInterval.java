// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.chrono;

import jakarta.annotation.Nonnull;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public record LocalDateInterval(@Nonnull LocalDate start, @Nonnull LocalDate end) { // Both boundaries are inclusive

    public LocalDateInterval {
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);
    }

    public static @Nonnull LocalDateInterval ofAddition(@Nonnull LocalDate start, @Nonnull Period periodToAdd) {
        Objects.requireNonNull(start);
        Objects.requireNonNull(periodToAdd);

        // FIXME: Take into consideration DateTimeException and ArithmeticException
        LocalDate end = start.plus(periodToAdd);
        return new LocalDateInterval(start, end);
    }

    public @Nonnull Period toPeriod() {
        // FIXME: LocalDateInterval end is inclusive, Period::between end is exclusive
        return Period.between(start, end);
    }

    public boolean isReversed() {
        return start.isAfter(end);
    }

    public @Nonnull LocalDateInterval reverse() {
        if (start.isEqual(end)) {
            return this;
        }

        return new LocalDateInterval(end, start);
    }
}
