// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class FrankfurterCurrencySetDTOMapperTests {

    @Test
    void mapsDTO() {
        var dto = Map.of(
                "EUR", "Euro",
                "CHF", "Swiss Franc"
        );
        var mapper = new FrankfurterCurrencySetDTOMapper();

        Set<Currency> expected = Set.of(
                new Currency("EUR", "Euro"),
                new Currency("CHF", "Swiss Franc")
        );
        Set<Currency> actual = mapper.map(dto);

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
