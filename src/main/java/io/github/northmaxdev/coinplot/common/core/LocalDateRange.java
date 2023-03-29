// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.common.core;

import jakarta.annotation.Nonnull;

import java.time.LocalDate;

public record LocalDateRange(@Nonnull LocalDate start, @Nonnull LocalDate end) {

    public LocalDateRange {
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("Start >= end");
        }
    }
}
