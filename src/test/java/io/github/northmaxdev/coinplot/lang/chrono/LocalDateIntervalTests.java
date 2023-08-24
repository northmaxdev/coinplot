// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.chrono;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class LocalDateIntervalTests {

    static final LocalDate JAN_1 = LocalDate.of(2000, Month.JANUARY, 1);
    static final LocalDate JAN_2 = LocalDate.of(2000, Month.JANUARY, 2);
    static final LocalDate JAN_13 = LocalDate.of(2000, Month.JANUARY, 13);
    static final LocalDate JAN_14 = LocalDate.of(2000, Month.JANUARY, 14);

    @ParameterizedTest
    @MethodSource("provideIsReversedExpectations")
    @DisplayName("isReversed returns the expected value for a given interval")
    void isReversedWhenExpected(LocalDate start, LocalDate end, boolean expected) {
        LocalDateInterval interval = new LocalDateInterval(start, end);

        assertThat(interval.isReversed()).isEqualTo(expected);
    }

    Stream<Arguments> provideIsReversedExpectations() {
        return Stream.of(
                arguments(JAN_1, JAN_1, false),
                arguments(JAN_1, JAN_2, false),
                arguments(JAN_1, JAN_14, false),
                arguments(JAN_14, JAN_1, true),
                arguments(JAN_14, JAN_13, true)
        );
    }

    @ParameterizedTest
    @MethodSource("providePeriods")
    @DisplayName("toPeriod returns the expected value for a given interval")
    void expectedPeriodEqualsActual(LocalDate start, LocalDate end, Period expected) {
        LocalDateInterval interval = new LocalDateInterval(start, end);

        assertThat(interval.toPeriod()).isEqualTo(expected);
    }

    Stream<Arguments> providePeriods() {
        return Stream.of(
                arguments(JAN_1, JAN_1, Period.ZERO),
                arguments(JAN_1, JAN_2, Period.ofDays(1)),
                arguments(JAN_1, JAN_14, Period.ofDays(13)),
                arguments(JAN_14, JAN_1, Period.ofDays(-13)),
                arguments(JAN_14, JAN_13, Period.ofDays(-1))
        );
    }
}
