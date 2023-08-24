// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRate;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.Set;

import static io.github.northmaxdev.coinplot.Tests.BAR_FRANC;
import static io.github.northmaxdev.coinplot.Tests.BAZ_POUND;
import static io.github.northmaxdev.coinplot.Tests.CURRENCY_SERVICE_MOCK;
import static io.github.northmaxdev.coinplot.Tests.FOO_DOLLAR;
import static org.assertj.core.api.Assertions.assertThat;

class FrankfurterExchangeRateSetDTOMapperTests {

    @Test
    void mapsDTO() {
        // Case:
        // Base = FOO
        // Targets = BAR, BAZ
        // Interval = [1st of January 2000, 2nd of January 2000]

        var dto = new FrankfurterExchangeRateSetDTO("FOO", Map.of(
                LocalDate.of(2000, Month.JANUARY, 1), Map.of(
                        "BAR", BigDecimal.valueOf(1.567),
                        "BAZ", BigDecimal.valueOf(2.301)
                ),
                LocalDate.of(2000, Month.JANUARY, 2), Map.of(
                        "BAR", BigDecimal.valueOf(1.578),
                        "BAZ", BigDecimal.valueOf(2.29)
                )
        ));
        var mapper = new FrankfurterExchangeRateSetDTOMapper(CURRENCY_SERVICE_MOCK);

        Set<ExchangeRate> expected = Set.of(
                new ExchangeRate(FOO_DOLLAR, BAR_FRANC, LocalDate.of(2000, Month.JANUARY, 1), BigDecimal.valueOf(1.567)),
                new ExchangeRate(FOO_DOLLAR, BAZ_POUND, LocalDate.of(2000, Month.JANUARY, 1), BigDecimal.valueOf(2.301)),
                new ExchangeRate(FOO_DOLLAR, BAR_FRANC, LocalDate.of(2000, Month.JANUARY, 2), BigDecimal.valueOf(1.578)),
                new ExchangeRate(FOO_DOLLAR, BAZ_POUND, LocalDate.of(2000, Month.JANUARY, 2), BigDecimal.valueOf(2.29))
        );
        Set<ExchangeRate> actual = mapper.map(dto);

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}