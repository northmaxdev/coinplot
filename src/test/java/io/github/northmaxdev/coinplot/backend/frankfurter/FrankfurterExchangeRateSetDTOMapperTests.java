// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRate;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.Set;

import static io.github.northmaxdev.coinplot.Tests.CURRENCY_SERVICE_MOCK;
import static io.github.northmaxdev.coinplot.Tests.FOO_TO_BAR_JAN_1_2000;
import static io.github.northmaxdev.coinplot.Tests.FOO_TO_BAR_JAN_2_2000;
import static io.github.northmaxdev.coinplot.Tests.FOO_TO_BAZ_JAN_1_2000;
import static io.github.northmaxdev.coinplot.Tests.FOO_TO_BAZ_JAN_2_2000;
import static org.assertj.core.api.Assertions.assertThat;

class FrankfurterExchangeRateSetDTOMapperTests {

    @Test
    void mapsDTO() {
        var dto = new FrankfurterExchangeRateSetDTO("FOO", Map.of(
                LocalDate.of(2000, Month.JANUARY, 1), Map.of(
                        "BAR", BigDecimal.valueOf(1.618),
                        "BAZ", BigDecimal.valueOf(3.1415)
                ),
                LocalDate.of(2000, Month.JANUARY, 2), Map.of(
                        "BAR", BigDecimal.valueOf(1.619),
                        "BAZ", BigDecimal.valueOf(3.1401)
                )
        ));
        var mapper = new FrankfurterExchangeRateSetDTOMapper(CURRENCY_SERVICE_MOCK);

        Set<ExchangeRate> expected = Set.of(
                FOO_TO_BAR_JAN_1_2000,
                FOO_TO_BAR_JAN_2_2000,
                FOO_TO_BAZ_JAN_1_2000,
                FOO_TO_BAZ_JAN_2_2000
        );
        Set<ExchangeRate> actual = mapper.map(dto);

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
