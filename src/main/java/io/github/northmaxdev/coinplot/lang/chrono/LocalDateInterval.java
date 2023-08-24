// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.chrono;

import jakarta.annotation.Nonnull;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

// Start inclusive, end exclusive
public record LocalDateInterval(@Nonnull LocalDate start, @Nonnull LocalDate end) {

    public LocalDateInterval {
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);
    }

    public static @Nonnull LocalDateInterval calculate(@Nonnull LocalDate start, @Nonnull Period periodToAdd)
            throws ArithmeticException {
        Objects.requireNonNull(start);
        Objects.requireNonNull(periodToAdd);

        // Can LocalDate.plus(Period) throw a DateTimeException or not?
        // The related JavaDocs do not seem to answer the question clearly.
        LocalDate end = start.plus(periodToAdd);
        return new LocalDateInterval(start, end);
    }

    public @Nonnull Period toPeriod() {
        return Period.between(start, end);
    }

    public boolean isReversed() {
        return start.isAfter(end);
    }

    public @Nonnull LocalDateInterval flipped() {
        if (start.isEqual(end)) {
            return this;
        }

        return new LocalDateInterval(end, start);
    }
}
