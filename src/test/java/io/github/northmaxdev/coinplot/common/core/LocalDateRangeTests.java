// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.common.core;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LocalDateRangeTests {

    @Test
    void throwsIAEIfEndBeforeStart() {
        assertThrows(IllegalArgumentException.class, () -> {
            var start = LocalDate.of(2000, 1, 3);
            var end = LocalDate.of(2000, 1, 2);
            new LocalDateRange(start, end);
        });
    }

    @Test
    void throwsIAEIfEndEqualsStart() {
        assertThrows(IllegalArgumentException.class, () -> {
            var start = LocalDate.of(2000, 1, 1);
            var end = LocalDate.of(2000, 1, 1);
            new LocalDateRange(start, end);
        });
    }

    @Test
    void doesNotThrowIfEndAfterStart() {
        assertDoesNotThrow(() -> {
            var start = LocalDate.of(2000, 1, 2);
            var end = LocalDate.of(2000, 1, 3);
            new LocalDateRange(start, end);
        });
    }
}
