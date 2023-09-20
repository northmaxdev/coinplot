// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;

import static io.github.northmaxdev.coinplot.Tests.CURRENCY_SERVICE_MOCK;
import static io.github.northmaxdev.coinplot.Tests.FOO_TO_BAR_JAN_1_2000;
import static io.github.northmaxdev.coinplot.Tests.FOO_TO_BAR_JAN_2_2000;
import static io.github.northmaxdev.coinplot.Tests.FOO_TO_BAZ_JAN_1_2000;
import static io.github.northmaxdev.coinplot.Tests.FOO_TO_BAZ_JAN_2_2000;
import static org.assertj.core.api.Assertions.assertThat;

class CommonExchangeRateSetDTOMapperTests {

    @Test
    void mapsDTO() {
        var dto = new CommonExchangeRateSetDTO("FOO", Map.of(
                LocalDate.of(2000, Month.JANUARY, 1), Map.of(
                        "BAR", BigDecimal.valueOf(1.618),
                        "BAZ", BigDecimal.valueOf(3.1415)
                ),
                LocalDate.of(2000, Month.JANUARY, 2), Map.of(
                        "BAR", BigDecimal.valueOf(1.619),
                        "BAZ", BigDecimal.valueOf(3.1401)
                )
        ));
        var mapper = new CommonExchangeRateSetDTOMapper(CURRENCY_SERVICE_MOCK);

        assertThat(mapper.map(dto)).containsExactlyInAnyOrder(
                FOO_TO_BAR_JAN_1_2000,
                FOO_TO_BAR_JAN_2_2000,
                FOO_TO_BAZ_JAN_1_2000,
                FOO_TO_BAZ_JAN_2_2000
        );
    }

    @Test
    @DisplayName("Returns an empty Set on unknown base currency code")
    void emptySetOnUnknownBase() {
        var dto = new CommonExchangeRateSetDTO("XTS", Map.of(
                LocalDate.of(2000, Month.JANUARY, 1), Map.of(
                        "BAR", BigDecimal.valueOf(1.618),
                        "BAZ", BigDecimal.valueOf(3.1415)
                ),
                LocalDate.of(2000, Month.JANUARY, 2), Map.of(
                        "BAR", BigDecimal.valueOf(1.619),
                        "BAZ", BigDecimal.valueOf(3.1401)
                )
        ));
        var mapper = new CommonExchangeRateSetDTOMapper(CURRENCY_SERVICE_MOCK);

        assertThat(mapper.map(dto)).isEmpty();
    }

    @Test
    @DisplayName("Skips unknown target currency codes")
    void skipsUnknownTargets() {
        var dto = new CommonExchangeRateSetDTO("FOO", Map.of(
                LocalDate.of(2000, Month.JANUARY, 1), Map.of(
                        "BAR", BigDecimal.valueOf(1.618),
                        "EUR", BigDecimal.valueOf(16.3264)
                ),
                LocalDate.of(2000, Month.JANUARY, 2), Map.of(
                        "BAR", BigDecimal.valueOf(1.619),
                        "EUR", BigDecimal.valueOf(16.6432)
                )
        ));
        var mapper = new CommonExchangeRateSetDTOMapper(CURRENCY_SERVICE_MOCK);

        assertThat(mapper.map(dto)).containsExactlyInAnyOrder(FOO_TO_BAR_JAN_1_2000, FOO_TO_BAR_JAN_2_2000);
    }
}
