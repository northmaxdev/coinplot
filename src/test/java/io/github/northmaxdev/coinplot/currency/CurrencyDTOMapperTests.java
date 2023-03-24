// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.currency;

import org.junit.jupiter.api.Test;

import java.util.List;
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

        // Note: may need to add an assumption for Currency::equals

        List<Currency> expected = List.of(
                new Currency("EUR", "Euro"),
                new Currency("CHF", "Swiss Franc")
        );
        List<Currency> actual = mapper.map(dto);

        assertThat(actual).isEqualTo(expected);
    }
}
