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

    public @Nonnull Period toPeriod() {
        return Period.between(start, end);
    }

    public @Nonnull Stream<LocalDate> stream() {
        return start.datesUntil(end);
    }

    @Override
    public String toString() {
        // TODO (Performance): This can be lazily-evaluated and cached
        return '[' + start.format(DateTimeFormatter.ISO_LOCAL_DATE) + ", " + end.format(DateTimeFormatter.ISO_LOCAL_DATE) + ')';
    }
}
