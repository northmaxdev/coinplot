// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.common.core;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

class LocalDateRangeTests {

    @Test
    void throwsIAEIfEndBeforeStart() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            var start = LocalDate.of(2000, 1, 3);
            var end = LocalDate.of(2000, 1, 2);
            new LocalDateRange(start, end);
        });
    }

    @Test
    void throwsIAEIfEndEqualsStart() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            var start = LocalDate.of(2000, 1, 1);
            var end = LocalDate.of(2000, 1, 1);
            new LocalDateRange(start, end);
        });
    }

    @Test
    void doesNotThrowIfEndAfterStart() {
        assertThatNoException().isThrownBy(() -> {
            var start = LocalDate.of(2000, 1, 2);
            var end = LocalDate.of(2000, 1, 3);
            new LocalDateRange(start, end);
        });
    }
}
