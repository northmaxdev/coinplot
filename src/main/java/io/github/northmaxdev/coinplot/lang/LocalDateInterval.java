// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;

import java.time.LocalDate;

public record LocalDateInterval(@Nonnull LocalDate start, @Nonnull LocalDate end) {

    public boolean isReversed() {
        return start.isAfter(end);
    }
}
