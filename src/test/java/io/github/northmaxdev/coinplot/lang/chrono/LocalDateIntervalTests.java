// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.chrono;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @Test
    @DisplayName("Method \"dates\" returns expected contents on ascending interval")
    void datesAscending() {
        LocalDateInterval interval = new LocalDateInterval(JAN_2, JAN_13);

        Stream<LocalDate> expected = Stream.of(
                LocalDate.of(2000, Month.JANUARY, 2),
                LocalDate.of(2000, Month.JANUARY, 3),
                LocalDate.of(2000, Month.JANUARY, 4),
                LocalDate.of(2000, Month.JANUARY, 5),
                LocalDate.of(2000, Month.JANUARY, 6),
                LocalDate.of(2000, Month.JANUARY, 7),
                LocalDate.of(2000, Month.JANUARY, 8),
                LocalDate.of(2000, Month.JANUARY, 9),
                LocalDate.of(2000, Month.JANUARY, 10),
                LocalDate.of(2000, Month.JANUARY, 11),
                LocalDate.of(2000, Month.JANUARY, 12)
                // Interval is end-exclusive, no 13
        );
        Stream<LocalDate> actual = interval.dates();

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected.toList());
    }

    @Test
    @DisplayName("Method \"dates\" returns expected contents on descending interval")
    void datesDescending() {
        LocalDateInterval interval = new LocalDateInterval(JAN_13, JAN_2);

        Stream<LocalDate> expected = Stream.of(
                LocalDate.of(2000, Month.JANUARY, 13),
                LocalDate.of(2000, Month.JANUARY, 12),
                LocalDate.of(2000, Month.JANUARY, 11),
                LocalDate.of(2000, Month.JANUARY, 10),
                LocalDate.of(2000, Month.JANUARY, 9),
                LocalDate.of(2000, Month.JANUARY, 8),
                LocalDate.of(2000, Month.JANUARY, 7),
                LocalDate.of(2000, Month.JANUARY, 6),
                LocalDate.of(2000, Month.JANUARY, 5),
                LocalDate.of(2000, Month.JANUARY, 4),
                LocalDate.of(2000, Month.JANUARY, 3)
                // Interval is end-exclusive, no 2
        );
        Stream<LocalDate> actual = interval.dates();

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected.toList());
    }

    @Test
    @DisplayName("Method \"dates\" returns an empty stream on equal start/end")
    void datesEmpty() {
        LocalDateInterval interval = new LocalDateInterval(JAN_13, JAN_13);

        assertThat(interval.dates()).isEmpty();
    }
}
