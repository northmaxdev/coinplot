// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.currency;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyDTOMapperTests {

    @Test
    void maps() {
        Map<String, String> dto = Map.of(
                "EUR", "Euro",
                "CHF", "Swiss Franc"
        );
        CurrencyDTOMapper mapper = new CurrencyDTOMapper();

        Map<String, Currency> expected = Map.of(
                "EUR", new Currency("EUR", "Euro"),
                "CHF", new Currency("CHF", "Swiss Franc")
        );
        Map<String, Currency> actual = mapper.map(dto);

        assertThat(actual).containsExactlyInAnyOrderEntriesOf(expected);
    }
}
