// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.stream.Stream;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

// Endpoints: start is inclusive, end is exclusive
// Mathematical notation: [start, end)
public record LocalDateInterval(LocalDate start, LocalDate end) {

    public LocalDateInterval {
        Objects.requireNonNull(start, "start must not be null");
        Objects.requireNonNull(end, "end must not be null");

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

    public long length() {
        // TODO: Consider a guaranteed O(1) impl
        return stream().count();
    }

    @Override
    public String toString() {
        return '[' + start.format(ISO_LOCAL_DATE) + ", " + end.format(ISO_LOCAL_DATE) + ')';
    }
}
