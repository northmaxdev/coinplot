// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.chrono;

import jakarta.annotation.Nonnull;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

// Endpoints: start is inclusive, end is exclusive
// Mathematical notation: [start, end)
public record LocalDateInterval(@Nonnull LocalDate start, @Nonnull LocalDate end) {

    public static final Comparator<LocalDateInterval> LENGTH_BASED_ORDER = Comparator.comparingInt(LocalDateInterval::length);

    public LocalDateInterval {
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);

        if (!start.isBefore(end)) {
            throw new IllegalArgumentException("start >= end");
        }
    }

    public @Nonnull Period toPeriod() {
        return Period.between(start, end);
    }

    public @Nonnull Stream<LocalDate> stream() {
        return start.datesUntil(end);
    }

    public @Nonnull SortedSet<LocalDate> toSortedSet() {
        return stream()
                .collect(collectingAndThen(
                        toCollection(TreeSet::new),
                        Collections::unmodifiableSortedSet
                ));
    }

    public int length() {
        return (int) stream().count(); // This may or may not be O(1)
    }

    @Override
    public @Nonnull String toString() {
        return '[' + start.format(ISO_LOCAL_DATE) + ", " + end.format(ISO_LOCAL_DATE) + ')';
    }
}
