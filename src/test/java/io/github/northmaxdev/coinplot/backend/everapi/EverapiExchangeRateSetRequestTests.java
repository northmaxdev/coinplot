// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import io.github.northmaxdev.coinplot.lang.chrono.LocalDateInterval;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import static io.github.northmaxdev.coinplot.TestUtilities.BAR_FRANC;
import static io.github.northmaxdev.coinplot.TestUtilities.BAZ_POUND;
import static io.github.northmaxdev.coinplot.TestUtilities.FOO_DOLLAR;
import static io.github.northmaxdev.coinplot.TestUtilities.assertExpectedURIsContainActual;
import static io.github.northmaxdev.coinplot.TestUtilities.verifyAPIRequestEquals;

class EverapiExchangeRateSetRequestTests {

    @Disabled("https://github.com/assertj/assertj/issues/3127")
    @Test
    void reqURI() {
        assertExpectedURIsContainActual(() -> {
                    // These dates are completely arbitrary and do not represent any events
                    LocalDate start = LocalDate.of(2004, Month.SEPTEMBER, 19);
                    LocalDate end = LocalDate.of(2006, Month.MAY, 3);
                    LocalDateInterval interval = new LocalDateInterval(start, end);
                    return new EverapiExchangeRateSetRequest("0", BAR_FRANC, Set.of(FOO_DOLLAR, BAZ_POUND), interval);
                },
                "https://api.currencyapi.com/v3/range?base_currency=BAR&currencies=FOO%2CBAZ&datetime_start=2004-09-19T00%3A00%3A00Z&datetime_end=2006-05-03T00%3A00%3A00Z",
                "https://api.currencyapi.com/v3/range?base_currency=BAR&currencies=FOO%2CBAZ&datetime_end=2006-05-03T00%3A00%3A00Z&datetime_start=2004-09-19T00%3A00%3A00Z",
                "https://api.currencyapi.com/v3/range?base_currency=BAR&datetime_start=2004-09-19T00%3A00%3A00Z&currencies=FOO%2CBAZ&datetime_end=2006-05-03T00%3A00%3A00Z",
                "https://api.currencyapi.com/v3/range?base_currency=BAR&datetime_start=2004-09-19T00%3A00%3A00Z&datetime_end=2006-05-03T00%3A00%3A00Z&currencies=FOO%2CBAZ",
                "https://api.currencyapi.com/v3/range?base_currency=BAR&datetime_end=2006-05-03T00%3A00%3A00Z&currencies=FOO%2CBAZ&datetime_start=2004-09-19T00%3A00%3A00Z",
                "https://api.currencyapi.com/v3/range?base_currency=BAR&datetime_end=2006-05-03T00%3A00%3A00Z&datetime_start=2004-09-19T00%3A00%3A00Z&currencies=FOO%2CBAZ",
                "https://api.currencyapi.com/v3/range?currencies=FOO%2CBAZ&base_currency=BAR&datetime_end=2006-05-03T00%3A00%3A00Z&datetime_start=2004-09-19T00%3A00%3A00Z",
                "https://api.currencyapi.com/v3/range?currencies=FOO%2CBAZ&base_currency=BAR&datetime_start=2004-09-19T00%3A00%3A00Z&datetime_end=2006-05-03T00%3A00%3A00Z",
                "https://api.currencyapi.com/v3/range?currencies=FOO%2CBAZ&datetime_start=2004-09-19T00%3A00%3A00Z&datetime_end=2006-05-03T00%3A00%3A00Z&base_currency=BAR",
                "https://api.currencyapi.com/v3/range?currencies=FOO%2CBAZ&datetime_start=2004-09-19T00%3A00%3A00Z&base_currency=BAR&datetime_end=2006-05-03T00%3A00%3A00Z",
                "https://api.currencyapi.com/v3/range?currencies=FOO%2CBAZ&datetime_end=2006-05-03T00%3A00%3A00Z&datetime_start=2004-09-19T00%3A00%3A00Z&base_currency=BAR",
                "https://api.currencyapi.com/v3/range?currencies=FOO%2CBAZ&datetime_end=2006-05-03T00%3A00%3A00Z&base_currency=BAR&datetime_start=2004-09-19T00%3A00%3A00Z",
                "https://api.currencyapi.com/v3/range?datetime_start=2004-09-19T00%3A00%3A00Z&base_currency=BAR&currencies=FOO%2CBAZ&datetime_end=2006-05-03T00%3A00%3A00Z",
                "https://api.currencyapi.com/v3/range?datetime_start=2004-09-19T00%3A00%3A00Z&base_currency=BAR&datetime_end=2006-05-03T00%3A00%3A00Z&currencies=FOO%2CBAZ",
                "https://api.currencyapi.com/v3/range?datetime_start=2004-09-19T00%3A00%3A00Z&currencies=FOO%2CBAZ&base_currency=BAR&datetime_end=2006-05-03T00%3A00%3A00Z",
                "https://api.currencyapi.com/v3/range?datetime_start=2004-09-19T00%3A00%3A00Z&currencies=FOO%2CBAZ&datetime_end=2006-05-03T00%3A00%3A00Z&base_currency=BAR",
                "https://api.currencyapi.com/v3/range?datetime_start=2004-09-19T00%3A00%3A00Z&datetime_end=2006-05-03T00%3A00%3A00Z&base_currency=BAR&currencies=FOO%2CBAZ",
                "https://api.currencyapi.com/v3/range?datetime_start=2004-09-19T00%3A00%3A00Z&datetime_end=2006-05-03T00%3A00%3A00Z&currencies=FOO%2CBAZ&base_currency=BAR",
                "https://api.currencyapi.com/v3/range?datetime_end=2006-05-03T00%3A00%3A00Z&base_currency=BAR&datetime_start=2004-09-19T00%3A00%3A00Z&currencies=FOO%2CBAZ",
                "https://api.currencyapi.com/v3/range?datetime_end=2006-05-03T00%3A00%3A00Z&base_currency=BAR&currencies=FOO%2CBAZ&datetime_start=2004-09-19T00%3A00%3A00Z",
                "https://api.currencyapi.com/v3/range?datetime_end=2006-05-03T00%3A00%3A00Z&currencies=FOO%2CBAZ&datetime_start=2004-09-19T00%3A00%3A00Z&base_currency=BAR",
                "https://api.currencyapi.com/v3/range?datetime_end=2006-05-03T00%3A00%3A00Z&currencies=FOO%2CBAZ&base_currency=BAR&datetime_start=2004-09-19T00%3A00%3A00Z",
                "https://api.currencyapi.com/v3/range?datetime_end=2006-05-03T00%3A00%3A00Z&datetime_start=2004-09-19T00%3A00%3A00Z&currencies=FOO%2CBAZ&base_currency=BAR",
                "https://api.currencyapi.com/v3/range?datetime_end=2006-05-03T00%3A00%3A00Z&datetime_start=2004-09-19T00%3A00%3A00Z&base_currency=BAR&currencies=FOO%2CBAZ");
    }

    @Test
    void eq() {
        verifyAPIRequestEquals(EverapiExchangeRateSetRequest.class);
    }
}
