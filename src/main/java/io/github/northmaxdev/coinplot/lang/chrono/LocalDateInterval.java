// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.chrono;

import jakarta.annotation.Nonnull;

import java.time.LocalDate;
import java.time.Period;

public record LocalDateInterval(@Nonnull LocalDate start, @Nonnull LocalDate end) { // Both inclusive

    public static LocalDateInterval ofStartPlusPeriod(@Nonnull LocalDate start, @Nonnull Period period) {
        // FIXME: Take into consideration DateTimeException and ArithmeticException
        LocalDate end = start.plus(period);
        return new LocalDateInterval(start, end);
    }

    public Period toPeriod() {
        // TODO: Cache this
        // FIXME: LocalDateInterval end is inclusive, Period::between end is exclusive
        return Period.between(start, end);
    }

    public boolean isReversed() {
        return start.isAfter(end);
    }

    public LocalDateInterval reverse() {
        if (start.isEqual(end)) {
            return this;
        }

        return new LocalDateInterval(end, start);
    }
}
