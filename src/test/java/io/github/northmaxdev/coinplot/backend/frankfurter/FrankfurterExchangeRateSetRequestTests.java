// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.lang.chrono.LocalDateInterval;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import static io.github.northmaxdev.coinplot.Tests.BAR_FRANC;
import static io.github.northmaxdev.coinplot.Tests.BAZ_POUND;
import static io.github.northmaxdev.coinplot.Tests.FOO_DOLLAR;
import static io.github.northmaxdev.coinplot.Tests.assertExpectedURIsContainActual;
import static io.github.northmaxdev.coinplot.Tests.verifyAPIRequestEquals;

class FrankfurterExchangeRateSetRequestTests {

    @Test
    void reqURI() {
        LocalDate start = LocalDate.of(2020, Month.JANUARY, 1);
        LocalDate end = LocalDate.of(2020, Month.JANUARY, 31);
        FrankfurterExchangeRateSetRequest request = new FrankfurterExchangeRateSetRequest(
                FOO_DOLLAR,
                Set.of(BAR_FRANC, BAZ_POUND),
                new LocalDateInterval(start, end)
        );

        assertExpectedURIsContainActual(request,
                // Targets might themselves be specified in varying orders
                "https://api.frankfurter.app/2020-01-01..2020-01-31?from=FOO&to=BAR%2CBAZ",
                "https://api.frankfurter.app/2020-01-01..2020-01-31?from=FOO&to=BAZ%2CBAR",
                "https://api.frankfurter.app/2020-01-01..2020-01-31?to=BAR%2CBAZ&from=FOO",
                "https://api.frankfurter.app/2020-01-01..2020-01-31?to=BAZ%2CBAR&from=FOO"
        );
    }

    @Test
    void eq() {
        verifyAPIRequestEquals(FrankfurterExchangeRateSetRequest.class);
    }
}
