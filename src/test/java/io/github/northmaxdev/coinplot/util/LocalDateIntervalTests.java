// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

class LocalDateIntervalTests {

    static final LocalDate JAN_1 = LocalDate.of(2000, Month.JANUARY, 1);
    static final LocalDate JAN_2 = LocalDate.of(2000, Month.JANUARY, 2);

    @Test
    @DisplayName("Constructor throws IAE if start > end")
    void constructorRejectsDescendingDates() {
        assertThatIllegalArgumentException().isThrownBy(() -> new LocalDateInterval(JAN_2, JAN_1));
    }

    @Test
    @DisplayName("Constructor throws IAE if start == end")
    void constructorRejectsEqualDates() {
        assertThatIllegalArgumentException().isThrownBy(() -> new LocalDateInterval(JAN_2, JAN_2));
    }

    @Test
    @DisplayName("Constructor does not throw if start < end")
    void constructorAcceptsAscendingDates() {
        assertThatNoException().isThrownBy(() -> new LocalDateInterval(JAN_1, JAN_2));
    }
}
