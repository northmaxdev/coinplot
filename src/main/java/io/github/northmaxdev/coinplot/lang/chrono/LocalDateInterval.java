// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.chrono;

import jakarta.annotation.Nonnull;

import java.time.LocalDate;

public record LocalDateInterval(@Nonnull LocalDate start, @Nonnull LocalDate end) { // Both inclusive

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
