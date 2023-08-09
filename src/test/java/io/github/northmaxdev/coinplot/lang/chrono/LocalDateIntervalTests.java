// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.chrono;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

class LocalDateIntervalTests {

    @Test
    @DisplayName("Method isReversed returns true if start is after end")
    void isReversedOnAfter() {
        LocalDate start = LocalDate.of(2000, Month.FEBRUARY, 1);
        LocalDate end = LocalDate.of(2000, Month.JANUARY, 1);
        LocalDateInterval dateRange = new LocalDateInterval(start, end);

        assertThat(dateRange.isReversed()).isTrue();
    }

    @Test
    @DisplayName("Method isReversed returns false if start is before end")
    void isNotReversedOnBefore() {
        LocalDate start = LocalDate.of(2000, Month.JANUARY, 1);
        LocalDate end = LocalDate.of(2000, Month.FEBRUARY, 1);
        LocalDateInterval dateRange = new LocalDateInterval(start, end);

        assertThat(dateRange.isReversed()).isFalse();
    }

    @Test
    @DisplayName("Method isReversed returns false if start is equal to end")
    void isNotReversedOnEqual() {
        LocalDate date = LocalDate.of(2000, Month.JANUARY, 1);
        LocalDateInterval dateRange = new LocalDateInterval(date, date);

        assertThat(dateRange.isReversed()).isFalse();
    }
}
