// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class FixerCurrencySetDTOMapperTests {

    @Test
    void mapsDTO() {
        FixerCurrencySetDTO dto = new FixerCurrencySetDTO(
                Map.of(
                        "FOO", "Foo Dollar",
                        "BAR", "Bar Pound"
                )
        );
        var mapper = new FixerCurrencySetDTOMapper();

        Set<Currency> expected = Set.of(
                new Currency("FOO", "Foo Dollar"),
                new Currency("BAR", "Bar Dollar")
        );
        Set<Currency> actual = mapper.map(dto);

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
