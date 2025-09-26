// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.langext;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toUnmodifiableSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

class LocalDateIntervalTests {

    static final LocalDate JAN_15TH_2000 = LocalDate.of(2000, Month.JANUARY, 15);
    static final LocalDate DEC_25TH_2001 = LocalDate.of(2001, Month.DECEMBER, 25);

    @Nested
    class Constructor {

        @Test
        void throwsExceptionIfStartGreaterThanEnd() {
            assertThatIllegalArgumentException().isThrownBy(() -> new LocalDateInterval(DEC_25TH_2001, JAN_15TH_2000));
        }

        @Test
        void throwsExceptionIfStartEqualsEnd() {
            assertThatIllegalArgumentException().isThrownBy(() -> new LocalDateInterval(DEC_25TH_2001, DEC_25TH_2001));
        }

        @Test
        void doesNotThrowExceptionIfStartLessThanEnd() {
            assertThatNoException().isThrownBy(() -> new LocalDateInterval(JAN_15TH_2000, DEC_25TH_2001));
        }
    }

    @Test
    void toPeriodReturnsExpectedValue() {
        LocalDateInterval interval = new LocalDateInterval(JAN_15TH_2000, DEC_25TH_2001);
        Period expectedPeriod = Period.of(1, 11, 10);

        // NOTE: This probably invokes Period::equals, which compares year/month/day numbers exactly.
        // This means a period of 15M is NOT considered equal to 1Y3M.
        // This could cause the assertion to fail even if semantically toPeriod() returns a correct result.
        assertThat(interval.toPeriod()).isEqualTo(expectedPeriod);
    }

    @Test
    void streamReturnsExpectedValues() {
        LocalDateInterval interval = new LocalDateInterval(
                LocalDate.of(2000, Month.JANUARY, 1),
                LocalDate.of(2000, Month.FEBRUARY, 1)
        );

        Set<LocalDate> expectedDates = IntStream.rangeClosed(1, 31)
                .mapToObj(dayOfMonth -> LocalDate.of(2000, Month.JANUARY, dayOfMonth))
                .collect(toUnmodifiableSet());

        assertThat(interval.stream()).containsExactlyInAnyOrderElementsOf(expectedDates);
    }

    @Test
    void countDaysReturnsExpectedValue() {
        LocalDateInterval interval = new LocalDateInterval(JAN_15TH_2000, DEC_25TH_2001);
        int expectedAmountOfDays = 710;

        assertThat(interval.countDays()).isEqualTo(expectedAmountOfDays);
    }

    @Test
    void equalsAndHashCodeContract() {
        LocalDate startPrefab1 = LocalDate.of(2000, Month.JANUARY, 1);
        LocalDate startPrefab2 = LocalDate.of(2000, Month.JANUARY, 2);
        LocalDate endPrefab1 = LocalDate.of(2000, Month.JANUARY, 15);
        LocalDate endPrefab2 = LocalDate.of(2000, Month.JANUARY, 16);

        EqualsVerifier.forClass(LocalDateInterval.class)
                .withPrefabValuesForField("start", startPrefab1, startPrefab2)
                .withPrefabValuesForField("end", endPrefab1, endPrefab2)
                .verify();
    }
}
