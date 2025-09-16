// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.langext;

import java.time.LocalDate;
import java.time.Period;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.util.Objects.requireNonNull;

/**
 * Interval of local dates.
 * <p>
 * Mathematical notation: {@code [start, end)}
 *
 * @param start <b>inclusive</b> start date, not {@code null}
 * @param end <b>exclusive</b> end date, not {@code null} and must be strictly <b>after</b> the start date
 */
public record LocalDateInterval(LocalDate start, LocalDate end) {

    public LocalDateInterval {
        requireNonNull(start, "start date must not be null");
        requireNonNull(end, "end date must not be null");

        if (!start.isBefore(end)) {
            throw new IllegalArgumentException("start >= end");
        }
    }

    public Period toPeriod() {
        return Period.between(start, end);
    }

    public Stream<LocalDate> stream() {
        return start.datesUntil(end);
    }

    public int countDays() {
        long count = LongStream.range(start.toEpochDay(), end.toEpochDay()).count();
        // A day count that is equal to Integer.MAX_VALUE is roughly 5,883,517 years,
        // which is enough for the vast majority of real-life practical applications.
        return Math.toIntExact(count);
    }

    @Override
    public String toString() {
        return '[' + start.format(ISO_LOCAL_DATE) + ", " + end.format(ISO_LOCAL_DATE) + ')';
    }
}
