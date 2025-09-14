// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.langext;

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

    // Long.MAX_VALUE in days is equal to 25252216391115060 years
    // Integer.MAX_VALUE in days is equal to 5879489.79 years
    // This method should realistically return an int instead of a long.
    public int length() {
        return Math.toIntExact(stream().count());
    }

    @Override
    public String toString() {
        return '[' + start.format(ISO_LOCAL_DATE) + ", " + end.format(ISO_LOCAL_DATE) + ')';
    }
}
