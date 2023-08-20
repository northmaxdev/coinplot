// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.everapi.EverapiCurrencySetDTO.CurrencyObject;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class EverapiCurrencySetDTOMapperTests {

    @Test
    void mapsDTO() {
        var dto = new EverapiCurrencySetDTO(Map.of(
                "FOO", new CurrencyObject("Foo Dollar", "*"),
                "BAR", new CurrencyObject("Bar Lira", "@"),
                "BAZ", new CurrencyObject("Baz Pound", "&")
        ));
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
