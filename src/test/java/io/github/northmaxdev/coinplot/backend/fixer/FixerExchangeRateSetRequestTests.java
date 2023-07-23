// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import io.github.northmaxdev.coinplot.lang.LocalDateInterval;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import static io.github.northmaxdev.coinplot.TestUtils.BAR_FRANC;
import static io.github.northmaxdev.coinplot.TestUtils.BAZ_POUND;
import static io.github.northmaxdev.coinplot.TestUtils.FOO_DOLLAR;
import static io.github.northmaxdev.coinplot.TestUtils.assertExpectedURIsContainActual;
import static io.github.northmaxdev.coinplot.TestUtils.verifyAPIRequestEquals;

class FixerExchangeRateSetRequestTests {

    @Disabled // FIXME: See related note in FrankfurterExchangeRateSetRequest tests
    @Test
    void reqURI() {
        assertExpectedURIsContainActual(() -> {
                    // These dates are completely arbitrary and do not represent any events
                    LocalDate start = LocalDate.of(1987, Month.APRIL, 6);
                    LocalDate end = LocalDate.of(1989, Month.NOVEMBER, 23);
                    LocalDateInterval interval = new LocalDateInterval(start, end);
                    return new FixerExchangeRateSetRequest("123", BAZ_POUND, Set.of(FOO_DOLLAR, BAR_FRANC), interval);
                },
                "https://data.fixer.io/api/timeseries?access_key=123&start_date=1987-04-06&end_date=1989-11-23&base=BAZ&symbols=FOO%2CBAR",
                "https://data.fixer.io/api/timeseries?access_key=123&start_date=1987-04-06&end_date=1989-11-23&symbols=FOO%2CBAR&base=BAZ",
                "https://data.fixer.io/api/timeseries?access_key=123&start_date=1987-04-06&base=BAZ&end_date=1989-11-23&symbols=FOO%2CBAR",
                "https://data.fixer.io/api/timeseries?access_key=123&start_date=1987-04-06&base=BAZ&symbols=FOO%2CBAR&end_date=1989-11-23",
                "https://data.fixer.io/api/timeseries?access_key=123&start_date=1987-04-06&symbols=FOO%2CBAR&end_date=1989-11-23&base=BAZ",
                "https://data.fixer.io/api/timeseries?access_key=123&start_date=1987-04-06&symbols=FOO%2CBAR&base=BAZ&end_date=1989-11-23",
                "https://data.fixer.io/api/timeseries?access_key=123&end_date=1989-11-23&start_date=1987-04-06&symbols=FOO%2CBAR&base=BAZ",
                "https://data.fixer.io/api/timeseries?access_key=123&end_date=1989-11-23&start_date=1987-04-06&base=BAZ&symbols=FOO%2CBAR",
                "https://data.fixer.io/api/timeseries?access_key=123&end_date=1989-11-23&base=BAZ&symbols=FOO%2CBAR&start_date=1987-04-06",
                "https://data.fixer.io/api/timeseries?access_key=123&end_date=1989-11-23&base=BAZ&start_date=1987-04-06&symbols=FOO%2CBAR",
                "https://data.fixer.io/api/timeseries?access_key=123&end_date=1989-11-23&symbols=FOO%2CBAR&base=BAZ&start_date=1987-04-06",
                "https://data.fixer.io/api/timeseries?access_key=123&end_date=1989-11-23&symbols=FOO%2CBAR&start_date=1987-04-06&base=BAZ",
                "https://data.fixer.io/api/timeseries?access_key=123&base=BAZ&start_date=1987-04-06&end_date=1989-11-23&symbols=FOO%2CBAR",
                "https://data.fixer.io/api/timeseries?access_key=123&base=BAZ&start_date=1987-04-06&symbols=FOO%2CBAR&end_date=1989-11-23",
                "https://data.fixer.io/api/timeseries?access_key=123&base=BAZ&end_date=1989-11-23&start_date=1987-04-06&symbols=FOO%2CBAR",
                "https://data.fixer.io/api/timeseries?access_key=123&base=BAZ&end_date=1989-11-23&symbols=FOO%2CBAR&start_date=1987-04-06",
                "https://data.fixer.io/api/timeseries?access_key=123&base=BAZ&symbols=FOO%2CBAR&start_date=1987-04-06&end_date=1989-11-23",
                "https://data.fixer.io/api/timeseries?access_key=123&base=BAZ&symbols=FOO%2CBAR&end_date=1989-11-23&start_date=1987-04-06",
                "https://data.fixer.io/api/timeseries?access_key=123&symbols=FOO%2CBAR&start_date=1987-04-06&base=BAZ&end_date=1989-11-23",
                "https://data.fixer.io/api/timeseries?access_key=123&symbols=FOO%2CBAR&start_date=1987-04-06&end_date=1989-11-23&base=BAZ",
                "https://data.fixer.io/api/timeseries?access_key=123&symbols=FOO%2CBAR&end_date=1989-11-23&base=BAZ&start_date=1987-04-06",
                "https://data.fixer.io/api/timeseries?access_key=123&symbols=FOO%2CBAR&end_date=1989-11-23&start_date=1987-04-06&base=BAZ",
                "https://data.fixer.io/api/timeseries?access_key=123&symbols=FOO%2CBAR&base=BAZ&end_date=1989-11-23&start_date=1987-04-06",
                "https://data.fixer.io/api/timeseries?access_key=123&symbols=FOO%2CBAR&base=BAZ&start_date=1987-04-06&end_date=1989-11-23",
                "https://data.fixer.io/api/timeseries?start_date=1987-04-06&access_key=123&symbols=FOO%2CBAR&end_date=1989-11-23&base=BAZ",
                "https://data.fixer.io/api/timeseries?start_date=1987-04-06&access_key=123&symbols=FOO%2CBAR&base=BAZ&end_date=1989-11-23",
                "https://data.fixer.io/api/timeseries?start_date=1987-04-06&access_key=123&end_date=1989-11-23&symbols=FOO%2CBAR&base=BAZ",
                "https://data.fixer.io/api/timeseries?start_date=1987-04-06&access_key=123&end_date=1989-11-23&base=BAZ&symbols=FOO%2CBAR",
                "https://data.fixer.io/api/timeseries?start_date=1987-04-06&access_key=123&base=BAZ&symbols=FOO%2CBAR&end_date=1989-11-23",
                "https://data.fixer.io/api/timeseries?start_date=1987-04-06&access_key=123&base=BAZ&end_date=1989-11-23&symbols=FOO%2CBAR",
                "https://data.fixer.io/api/timeseries?start_date=1987-04-06&end_date=1989-11-23&symbols=FOO%2CBAR&base=BAZ&access_key=123",
                "https://data.fixer.io/api/timeseries?start_date=1987-04-06&end_date=1989-11-23&symbols=FOO%2CBAR&access_key=123&base=BAZ",
                "https://data.fixer.io/api/timeseries?start_date=1987-04-06&end_date=1989-11-23&access_key=123&base=BAZ&symbols=FOO%2CBAR",
                "https://data.fixer.io/api/timeseries?start_date=1987-04-06&end_date=1989-11-23&access_key=123&symbols=FOO%2CBAR&base=BAZ",
                "https://data.fixer.io/api/timeseries?start_date=1987-04-06&end_date=1989-11-23&base=BAZ&access_key=123&symbols=FOO%2CBAR",
                "https://data.fixer.io/api/timeseries?start_date=1987-04-06&end_date=1989-11-23&base=BAZ&symbols=FOO%2CBAR&access_key=123",
                "https://data.fixer.io/api/timeseries?start_date=1987-04-06&base=BAZ&symbols=FOO%2CBAR&access_key=123&end_date=1989-11-23",
                "https://data.fixer.io/api/timeseries?start_date=1987-04-06&base=BAZ&symbols=FOO%2CBAR&end_date=1989-11-23&access_key=123",
                "https://data.fixer.io/api/timeseries?start_date=1987-04-06&base=BAZ&access_key=123&symbols=FOO%2CBAR&end_date=1989-11-23",
                "https://data.fixer.io/api/timeseries?start_date=1987-04-06&base=BAZ&access_key=123&end_date=1989-11-23&symbols=FOO%2CBAR",
                "https://data.fixer.io/api/timeseries?start_date=1987-04-06&base=BAZ&end_date=1989-11-23&symbols=FOO%2CBAR&access_key=123",
                "https://data.fixer.io/api/timeseries?start_date=1987-04-06&base=BAZ&end_date=1989-11-23&access_key=123&symbols=FOO%2CBAR",
                "https://data.fixer.io/api/timeseries?start_date=1987-04-06&symbols=FOO%2CBAR&base=BAZ&end_date=1989-11-23&access_key=123",
                "https://data.fixer.io/api/timeseries?start_date=1987-04-06&symbols=FOO%2CBAR&base=BAZ&access_key=123&end_date=1989-11-23",
                "https://data.fixer.io/api/timeseries?start_date=1987-04-06&symbols=FOO%2CBAR&access_key=123&end_date=1989-11-23&base=BAZ",
                "https://data.fixer.io/api/timeseries?start_date=1987-04-06&symbols=FOO%2CBAR&access_key=123&base=BAZ&end_date=1989-11-23",
                "https://data.fixer.io/api/timeseries?start_date=1987-04-06&symbols=FOO%2CBAR&end_date=1989-11-23&access_key=123&base=BAZ",
                "https://data.fixer.io/api/timeseries?start_date=1987-04-06&symbols=FOO%2CBAR&end_date=1989-11-23&base=BAZ&access_key=123",
                "https://data.fixer.io/api/timeseries?end_date=1989-11-23&access_key=123&base=BAZ&symbols=FOO%2CBAR&start_date=1987-04-06",
                "https://data.fixer.io/api/timeseries?end_date=1989-11-23&access_key=123&base=BAZ&start_date=1987-04-06&symbols=FOO%2CBAR",
                "https://data.fixer.io/api/timeseries?end_date=1989-11-23&access_key=123&symbols=FOO%2CBAR&base=BAZ&start_date=1987-04-06",
                "https://data.fixer.io/api/timeseries?end_date=1989-11-23&access_key=123&symbols=FOO%2CBAR&start_date=1987-04-06&base=BAZ",
                "https://data.fixer.io/api/timeseries?end_date=1989-11-23&access_key=123&start_date=1987-04-06&base=BAZ&symbols=FOO%2CBAR",
                "https://data.fixer.io/api/timeseries?end_date=1989-11-23&access_key=123&start_date=1987-04-06&symbols=FOO%2CBAR&base=BAZ",
                "https://data.fixer.io/api/timeseries?end_date=1989-11-23&start_date=1987-04-06&base=BAZ&access_key=123&symbols=FOO%2CBAR",
                "https://data.fixer.io/api/timeseries?end_date=1989-11-23&start_date=1987-04-06&base=BAZ&symbols=FOO%2CBAR&access_key=123",
                "https://data.fixer.io/api/timeseries?end_date=1989-11-23&start_date=1987-04-06&symbols=FOO%2CBAR&access_key=123&base=BAZ",
                "https://data.fixer.io/api/timeseries?end_date=1989-11-23&start_date=1987-04-06&symbols=FOO%2CBAR&base=BAZ&access_key=123",
                "https://data.fixer.io/api/timeseries?end_date=1989-11-23&start_date=1987-04-06&access_key=123&symbols=FOO%2CBAR&base=BAZ",
                "https://data.fixer.io/api/timeseries?end_date=1989-11-23&start_date=1987-04-06&access_key=123&base=BAZ&symbols=FOO%2CBAR",
                "https://data.fixer.io/api/timeseries?end_date=1989-11-23&base=BAZ&start_date=1987-04-06&symbols=FOO%2CBAR&access_key=123",
                "https://data.fixer.io/api/timeseries?end_date=1989-11-23&base=BAZ&start_date=1987-04-06&access_key=123&symbols=FOO%2CBAR",
                "https://data.fixer.io/api/timeseries?end_date=1989-11-23&base=BAZ&symbols=FOO%2CBAR&start_date=1987-04-06&access_key=123",
                "https://data.fixer.io/api/timeseries?end_date=1989-11-23&base=BAZ&symbols=FOO%2CBAR&access_key=123&start_date=1987-04-06",
                "https://data.fixer.io/api/timeseries?end_date=1989-11-23&base=BAZ&access_key=123&start_date=1987-04-06&symbols=FOO%2CBAR",
                "https://data.fixer.io/api/timeseries?end_date=1989-11-23&base=BAZ&access_key=123&symbols=FOO%2CBAR&start_date=1987-04-06",
                "https://data.fixer.io/api/timeseries?end_date=1989-11-23&symbols=FOO%2CBAR&start_date=1987-04-06&access_key=123&base=BAZ",
                "https://data.fixer.io/api/timeseries?end_date=1989-11-23&symbols=FOO%2CBAR&start_date=1987-04-06&base=BAZ&access_key=123",
                "https://data.fixer.io/api/timeseries?end_date=1989-11-23&symbols=FOO%2CBAR&base=BAZ&access_key=123&start_date=1987-04-06",
                "https://data.fixer.io/api/timeseries?end_date=1989-11-23&symbols=FOO%2CBAR&base=BAZ&start_date=1987-04-06&access_key=123",
                "https://data.fixer.io/api/timeseries?end_date=1989-11-23&symbols=FOO%2CBAR&access_key=123&base=BAZ&start_date=1987-04-06",
                "https://data.fixer.io/api/timeseries?end_date=1989-11-23&symbols=FOO%2CBAR&access_key=123&start_date=1987-04-06&base=BAZ",
                "https://data.fixer.io/api/timeseries?base=BAZ&access_key=123&start_date=1987-04-06&end_date=1989-11-23&symbols=FOO%2CBAR",
                "https://data.fixer.io/api/timeseries?base=BAZ&access_key=123&start_date=1987-04-06&symbols=FOO%2CBAR&end_date=1989-11-23",
                "https://data.fixer.io/api/timeseries?base=BAZ&access_key=123&end_date=1989-11-23&start_date=1987-04-06&symbols=FOO%2CBAR",
                "https://data.fixer.io/api/timeseries?base=BAZ&access_key=123&end_date=1989-11-23&symbols=FOO%2CBAR&start_date=1987-04-06",
                "https://data.fixer.io/api/timeseries?base=BAZ&access_key=123&symbols=FOO%2CBAR&start_date=1987-04-06&end_date=1989-11-23",
                "https://data.fixer.io/api/timeseries?base=BAZ&access_key=123&symbols=FOO%2CBAR&end_date=1989-11-23&start_date=1987-04-06",
                "https://data.fixer.io/api/timeseries?base=BAZ&start_date=1987-04-06&access_key=123&symbols=FOO%2CBAR&end_date=1989-11-23",
                "https://data.fixer.io/api/timeseries?base=BAZ&start_date=1987-04-06&access_key=123&end_date=1989-11-23&symbols=FOO%2CBAR",
                "https://data.fixer.io/api/timeseries?base=BAZ&start_date=1987-04-06&end_date=1989-11-23&symbols=FOO%2CBAR&access_key=123",
                "https://data.fixer.io/api/timeseries?base=BAZ&start_date=1987-04-06&end_date=1989-11-23&access_key=123&symbols=FOO%2CBAR",
                "https://data.fixer.io/api/timeseries?base=BAZ&start_date=1987-04-06&symbols=FOO%2CBAR&end_date=1989-11-23&access_key=123",
                "https://data.fixer.io/api/timeseries?base=BAZ&start_date=1987-04-06&symbols=FOO%2CBAR&access_key=123&end_date=1989-11-23",
                "https://data.fixer.io/api/timeseries?base=BAZ&end_date=1989-11-23&access_key=123&start_date=1987-04-06&symbols=FOO%2CBAR",
                "https://data.fixer.io/api/timeseries?base=BAZ&end_date=1989-11-23&access_key=123&symbols=FOO%2CBAR&start_date=1987-04-06",
                "https://data.fixer.io/api/timeseries?base=BAZ&end_date=1989-11-23&start_date=1987-04-06&access_key=123&symbols=FOO%2CBAR",
                "https://data.fixer.io/api/timeseries?base=BAZ&end_date=1989-11-23&start_date=1987-04-06&symbols=FOO%2CBAR&access_key=123",
                "https://data.fixer.io/api/timeseries?base=BAZ&end_date=1989-11-23&symbols=FOO%2CBAR&access_key=123&start_date=1987-04-06",
                "https://data.fixer.io/api/timeseries?base=BAZ&end_date=1989-11-23&symbols=FOO%2CBAR&start_date=1987-04-06&access_key=123",
                "https://data.fixer.io/api/timeseries?base=BAZ&symbols=FOO%2CBAR&access_key=123&end_date=1989-11-23&start_date=1987-04-06",
                "https://data.fixer.io/api/timeseries?base=BAZ&symbols=FOO%2CBAR&access_key=123&start_date=1987-04-06&end_date=1989-11-23",
                "https://data.fixer.io/api/timeseries?base=BAZ&symbols=FOO%2CBAR&start_date=1987-04-06&end_date=1989-11-23&access_key=123",
                "https://data.fixer.io/api/timeseries?base=BAZ&symbols=FOO%2CBAR&start_date=1987-04-06&access_key=123&end_date=1989-11-23",
                "https://data.fixer.io/api/timeseries?base=BAZ&symbols=FOO%2CBAR&end_date=1989-11-23&start_date=1987-04-06&access_key=123",
                "https://data.fixer.io/api/timeseries?base=BAZ&symbols=FOO%2CBAR&end_date=1989-11-23&access_key=123&start_date=1987-04-06",
                "https://data.fixer.io/api/timeseries?symbols=FOO%2CBAR&access_key=123&base=BAZ&start_date=1987-04-06&end_date=1989-11-23",
                "https://data.fixer.io/api/timeseries?symbols=FOO%2CBAR&access_key=123&base=BAZ&end_date=1989-11-23&start_date=1987-04-06",
                "https://data.fixer.io/api/timeseries?symbols=FOO%2CBAR&access_key=123&start_date=1987-04-06&base=BAZ&end_date=1989-11-23",
                "https://data.fixer.io/api/timeseries?symbols=FOO%2CBAR&access_key=123&start_date=1987-04-06&end_date=1989-11-23&base=BAZ",
                "https://data.fixer.io/api/timeseries?symbols=FOO%2CBAR&access_key=123&end_date=1989-11-23&base=BAZ&start_date=1987-04-06",
                "https://data.fixer.io/api/timeseries?symbols=FOO%2CBAR&access_key=123&end_date=1989-11-23&start_date=1987-04-06&base=BAZ",
                "https://data.fixer.io/api/timeseries?symbols=FOO%2CBAR&start_date=1987-04-06&base=BAZ&end_date=1989-11-23&access_key=123",
                "https://data.fixer.io/api/timeseries?symbols=FOO%2CBAR&start_date=1987-04-06&base=BAZ&access_key=123&end_date=1989-11-23",
                "https://data.fixer.io/api/timeseries?symbols=FOO%2CBAR&start_date=1987-04-06&access_key=123&end_date=1989-11-23&base=BAZ",
                "https://data.fixer.io/api/timeseries?symbols=FOO%2CBAR&start_date=1987-04-06&access_key=123&base=BAZ&end_date=1989-11-23",
                "https://data.fixer.io/api/timeseries?symbols=FOO%2CBAR&start_date=1987-04-06&end_date=1989-11-23&access_key=123&base=BAZ",
                "https://data.fixer.io/api/timeseries?symbols=FOO%2CBAR&start_date=1987-04-06&end_date=1989-11-23&base=BAZ&access_key=123",
                "https://data.fixer.io/api/timeseries?symbols=FOO%2CBAR&end_date=1989-11-23&base=BAZ&access_key=123&start_date=1987-04-06",
                "https://data.fixer.io/api/timeseries?symbols=FOO%2CBAR&end_date=1989-11-23&base=BAZ&start_date=1987-04-06&access_key=123",
                "https://data.fixer.io/api/timeseries?symbols=FOO%2CBAR&end_date=1989-11-23&access_key=123&base=BAZ&start_date=1987-04-06",
                "https://data.fixer.io/api/timeseries?symbols=FOO%2CBAR&end_date=1989-11-23&access_key=123&start_date=1987-04-06&base=BAZ",
                "https://data.fixer.io/api/timeseries?symbols=FOO%2CBAR&end_date=1989-11-23&start_date=1987-04-06&base=BAZ&access_key=123",
                "https://data.fixer.io/api/timeseries?symbols=FOO%2CBAR&end_date=1989-11-23&start_date=1987-04-06&access_key=123&base=BAZ",
                "https://data.fixer.io/api/timeseries?symbols=FOO%2CBAR&base=BAZ&end_date=1989-11-23&start_date=1987-04-06&access_key=123",
                "https://data.fixer.io/api/timeseries?symbols=FOO%2CBAR&base=BAZ&end_date=1989-11-23&access_key=123&start_date=1987-04-06",
                "https://data.fixer.io/api/timeseries?symbols=FOO%2CBAR&base=BAZ&access_key=123&start_date=1987-04-06&end_date=1989-11-23",
                "https://data.fixer.io/api/timeseries?symbols=FOO%2CBAR&base=BAZ&access_key=123&end_date=1989-11-23&start_date=1987-04-06",
                "https://data.fixer.io/api/timeseries?symbols=FOO%2CBAR&base=BAZ&start_date=1987-04-06&access_key=123&end_date=1989-11-23",
                "https://data.fixer.io/api/timeseries?symbols=FOO%2CBAR&base=BAZ&start_date=1987-04-06&end_date=1989-11-23&access_key=123");
    }

    @Test
    void eq() {
        verifyAPIRequestEquals(FixerExchangeRateSetRequest.class);
    }
}
