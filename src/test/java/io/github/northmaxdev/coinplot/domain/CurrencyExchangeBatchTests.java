// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import io.github.northmaxdev.coinplot.util.LocalDateInterval;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Currency;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyExchangeBatchTests {

    @Test
    @DisplayName("stream() produces expected results in any order")
    void streamProducesExpectedResultsInAnyOrder() {
        Currency euro = Currency.getInstance("EUR");
        Currency swissFranc = Currency.getInstance("CHF");
        Currency canadianDollar = Currency.getInstance("CAD");

        CurrencyExchangeBatch batch = new CurrencyExchangeBatch(
                euro,
                Set.of(swissFranc, canadianDollar),
                new LocalDateInterval(
                        LocalDate.of(2000, Month.JANUARY, 1),
                        LocalDate.of(2000, Month.JANUARY, 4)
                )
        );

        Stream<CurrencyExchange> stream = batch.stream();

        assertThat(stream).containsExactlyInAnyOrder(
                new CurrencyExchange(euro, swissFranc, LocalDate.of(2000, Month.JANUARY, 1)),
                new CurrencyExchange(euro, canadianDollar, LocalDate.of(2000, Month.JANUARY, 1)),
                new CurrencyExchange(euro, swissFranc, LocalDate.of(2000, Month.JANUARY, 2)),
                new CurrencyExchange(euro, canadianDollar, LocalDate.of(2000, Month.JANUARY, 2)),
                new CurrencyExchange(euro, swissFranc, LocalDate.of(2000, Month.JANUARY, 3)),
                new CurrencyExchange(euro, canadianDollar, LocalDate.of(2000, Month.JANUARY, 3))
        );
    }
}
