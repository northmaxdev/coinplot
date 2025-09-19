// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import io.github.northmaxdev.coinplot.langext.LocalDateInterval;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Currency;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class DatedExchangeZipTests {

    static Currency EURO = Currency.getInstance("EUR");
    static Currency SWISS_FRANC = Currency.getInstance("CHF");
    static Currency CANADIAN_DOLLAR = Currency.getInstance("CAD");

    @Test
    void streamReturnsExpectedValues() {
        DatedExchangeZip zip = new DatedExchangeZip(
                EURO,
                Set.of(SWISS_FRANC, CANADIAN_DOLLAR),
                new LocalDateInterval(
                        LocalDate.of(2000, Month.JANUARY, 1),
                        LocalDate.of(2000, Month.JANUARY, 4)
                )
        );

        assertThat(zip.stream()).containsExactlyInAnyOrder(
                new DatedExchange(EURO, SWISS_FRANC, LocalDate.of(2000, Month.JANUARY, 1)),
                new DatedExchange(EURO, CANADIAN_DOLLAR, LocalDate.of(2000, Month.JANUARY, 1)),
                new DatedExchange(EURO, SWISS_FRANC, LocalDate.of(2000, Month.JANUARY, 2)),
                new DatedExchange(EURO, CANADIAN_DOLLAR, LocalDate.of(2000, Month.JANUARY, 2)),
                new DatedExchange(EURO, SWISS_FRANC, LocalDate.of(2000, Month.JANUARY, 3)),
                new DatedExchange(EURO, CANADIAN_DOLLAR, LocalDate.of(2000, Month.JANUARY, 3))
        );
    }

    @Test
    void sizeReturnsExpectedValue() {
        DatedExchangeZip zip = new DatedExchangeZip(
                EURO,
                Set.of(SWISS_FRANC, CANADIAN_DOLLAR),
                new LocalDateInterval(
                        LocalDate.of(2000, Month.JANUARY, 1),
                        LocalDate.of(2000, Month.FEBRUARY, 1)
                )
        );

        assertThat(zip.size()).isEqualTo(62);
    }

    @Test
    void equalsAndHashCodeContract() {
        EqualsVerifier.forClass(DatedExchangeZip.class).verify();
    }
}
