// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.chrono;

import jakarta.annotation.Nonnull;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

// Endpoints: start is inclusive, end is exclusive
// Mathematical notation: [start, end)
public record LocalDateInterval(@Nonnull LocalDate start, @Nonnull LocalDate end) {

    public LocalDateInterval {
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);

        if (!start.isBefore(end)) {
            throw new IllegalArgumentException("start >= end");
        }
    }

    public static @Nonnull Comparator<LocalDateInterval> comparingLength() {
        return Comparator.comparingLong(LocalDateInterval::length);
    }

    public @Nonnull Period toPeriod() {
        return Period.between(start, end);
    }

    public @Nonnull Stream<LocalDate> stream() {
        return start.datesUntil(end);
    }

    public long length() {
        return stream().count(); // TODO: An O(1) implementation
    }

    @Override
    public @Nonnull String toString() {
        return '[' + start.format(ISO_LOCAL_DATE) + ", " + end.format(ISO_LOCAL_DATE) + ')';
    }
}
