// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.chrono;

import jakarta.annotation.Nonnull;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.stream.Stream;

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

    public @Nonnull Stream<LocalDate> dates() {
        if (start.isBefore(end)) {
            return start.datesUntil(end);
        }

        // Given an interval of [2000-01-13, 2000-01-02), the resulting stream should include
        // 13th of Jan and exclude 2nd of Jan, but simply calling end.datesUntil(start) produces
        // a stream that is equivalent to the one produced if start < end. To solve this, we can
        // simply shift both endpoints to the right by 1 day.
        long daysToAdd = 1L;
        LocalDate shiftedStart = start.plusDays(daysToAdd);
        LocalDate shiftedEnd = end.plusDays(daysToAdd);
        return shiftedEnd.datesUntil(shiftedStart);

        // An alternative implementation:
        //    return end.datesUntil(start)
        //            .map(date -> date.plusDays(1L));
    }
}
