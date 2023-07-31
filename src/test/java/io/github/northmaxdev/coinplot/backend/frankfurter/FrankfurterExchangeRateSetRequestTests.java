// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.lang.LocalDateInterval;
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

class FrankfurterExchangeRateSetRequestTests {

    @Disabled("https://github.com/assertj/assertj/issues/3127")
    @Test
    void reqURI() {
        assertExpectedURIsContainActual(() -> {
                    // These dates are completely arbitrary and do not represent any events
                    LocalDate start = LocalDate.of(2020, Month.JANUARY, 1);
                    LocalDate end = LocalDate.of(2020, Month.JANUARY, 31);
                    LocalDateInterval interval = new LocalDateInterval(start, end);
                    return new FrankfurterExchangeRateSetRequest(FOO_DOLLAR, Set.of(BAR_FRANC, BAZ_POUND), interval);
                },
                "https://api.frankfurter.app/2020-01-01..2020-01-31?from=FOO&to=BAR%2CBAZ",
                "https://api.frankfurter.app/2020-01-01..2020-01-31?to=BAR%2CBAZ&from=FOO"
        );
    }

    @Test
    void eq() {
        verifyAPIRequestEquals(FrankfurterExchangeRateSetRequest.class);
    }
}
