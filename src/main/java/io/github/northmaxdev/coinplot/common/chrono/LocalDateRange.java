// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.common.chrono;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

public record LocalDateRange(@Nonnull LocalDate start, @Nonnull LocalDate end) {

    public LocalDateRange {
        if (!isValid(start, end)) {
            throw new IllegalArgumentException("Start date must come before end date");
        }
    }

    public static boolean isValid(@Nullable LocalDate start, @Nullable LocalDate end) {
        return start != null && end != null && start.isBefore(end);
    }
}
