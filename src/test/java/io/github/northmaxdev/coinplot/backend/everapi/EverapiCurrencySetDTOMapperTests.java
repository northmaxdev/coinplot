// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class EverapiCurrencySetDTOMapperTests {

    @Test
    void mapsDTO() {
        EverapiCurrencySetDTO dto = new EverapiCurrencySetDTO(
                Map.of(
                        "FOO", new EverapiCurrencySetDTO.CurrencyData("Foo Dollar", "*"),
                        "BAR", new EverapiCurrencySetDTO.CurrencyData("Bar Lira", "@"),
                        "BAZ", new EverapiCurrencySetDTO.CurrencyData("Baz Pound", "&")
                )
        );
        var mapper = new EverapiCurrencySetDTOMapper();

        Set<Currency> expected = Set.of(
                new Currency("FOO", "Foo Dollar", "*"),
                new Currency("BAR", "Bar Lira", "@"),
                new Currency("BAZ", "Baz Pound", "&")
        );
        Set<Currency> actual = mapper.map(dto);

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
