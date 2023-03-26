// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.currency;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyDTOMapperTests {

    @Test
    void maps() {
        Map<String, String> dto = Map.of(
                "EUR", "Euro",
                "CHF", "Swiss Franc"
        );
        CurrencyDTOMapper mapper = new CurrencyDTOMapper();

        Set<Currency> expected = Set.of(
                new Currency("EUR", "Euro"),
                new Currency("CHF", "Swiss Franc")
        );
        Set<Currency> actual = mapper.map(dto);

        assertThat(actual).isEqualTo(expected);
    }
}
