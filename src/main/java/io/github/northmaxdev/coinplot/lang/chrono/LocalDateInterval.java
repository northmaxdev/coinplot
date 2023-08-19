// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.chrono;

import jakarta.annotation.Nonnull;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public record LocalDateInterval(@Nonnull LocalDate start, @Nonnull LocalDate end) { // Both inclusive

    public LocalDateInterval {
        Objects.requireNonNull(start, "start is null");
        Objects.requireNonNull(end, "end is null");
    }

    public static @Nonnull LocalDateInterval ofStartPlusPeriod(@Nonnull LocalDate start, @Nonnull Period period) {
        Objects.requireNonNull(start, "start is null");
        Objects.requireNonNull(period, "period is null");

        // FIXME: Take into consideration DateTimeException and ArithmeticException
        LocalDate end = start.plus(period);
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
