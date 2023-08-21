// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRate;
import io.github.northmaxdev.coinplot.backend.everapi.EverapiExchangeRateSetDTO.DataObject;
import io.github.northmaxdev.coinplot.backend.everapi.EverapiExchangeRateSetDTO.DataObject.ExchangeRateObject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.github.northmaxdev.coinplot.TestUtilities.BAR_FRANC;
import static io.github.northmaxdev.coinplot.TestUtilities.BAZ_POUND;
import static io.github.northmaxdev.coinplot.TestUtilities.CURRENCY_SERVICE_MOCK;
import static io.github.northmaxdev.coinplot.TestUtilities.FOO_DOLLAR;
import static java.time.Month.JANUARY;
import static org.assertj.core.api.Assertions.assertThat;

class EverapiExchangeRateSetDTOMapperTests {

    @Test
    void mapsDTO() {
        // Scenario:
        // Base = FOO
        // Targets = BAR, BAZ
        // Interval = [1st of January 2000, 2nd of January 2000]
        var dto = new EverapiExchangeRateSetDTO(List.of(
                new DataObject(
                        LocalDateTime.of(2000, JANUARY, 1, 0, 0),
                        Map.of(
                                "BAR", new ExchangeRateObject(BigDecimal.valueOf(1.0514)),
                                "BAZ", new ExchangeRateObject(BigDecimal.valueOf(2.431))
                        )
                ),
                new DataObject(
                        LocalDateTime.of(2000, JANUARY, 2, 0, 0),
                        Map.of(
                                "BAR", new ExchangeRateObject(BigDecimal.valueOf(1.0516)),
                                "BAZ", new ExchangeRateObject(BigDecimal.valueOf(2.432))
                        )
                )
        ));
        var mapper = new EverapiExchangeRateSetDTOMapper(CURRENCY_SERVICE_MOCK);

        Set<ExchangeRate> expected = Set.of(
                new ExchangeRate(FOO_DOLLAR, BAR_FRANC, LocalDate.of(2000, JANUARY, 1), BigDecimal.valueOf(1.0514)),
                new ExchangeRate(FOO_DOLLAR, BAZ_POUND, LocalDate.of(2000, JANUARY, 1), BigDecimal.valueOf(2.431)),
                new ExchangeRate(FOO_DOLLAR, BAR_FRANC, LocalDate.of(2000, JANUARY, 2), BigDecimal.valueOf(1.0516)),
                new ExchangeRate(FOO_DOLLAR, BAZ_POUND, LocalDate.of(2000, JANUARY, 2), BigDecimal.valueOf(2.432))
        );
        Set<ExchangeRate> actual = mapper.map(dto);

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
