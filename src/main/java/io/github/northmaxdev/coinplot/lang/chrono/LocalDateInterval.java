// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.chrono;

import jakarta.annotation.Nonnull;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.stream.Stream;

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

    public static @Nonnull LocalDateInterval calculate(@Nonnull LocalDate start, @Nonnull Period periodToAdd) {
        Objects.requireNonNull(start);
        Objects.requireNonNull(periodToAdd);

        LocalDate end = start.plus(periodToAdd);
        // No need to validate the period, the canonical constructor's bounds-check is enough
        return new LocalDateInterval(start, end);
    }

    public @Nonnull Period toPeriod() {
        return Period.between(start, end);
    }

    public @Nonnull Stream<LocalDate> dates() {
        return start.datesUntil(end);
    }

    @Override
    public String toString() {
        // TODO: This can be lazily-evaluated and cached
        return '[' + start.format(DateTimeFormatter.ISO_LOCAL_DATE) + ", " + end.format(DateTimeFormatter.ISO_LOCAL_DATE) + ')';
    }
}
