// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.currency;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyAPICurrenciesDTOMapperTests {

    @Test
    void maps() {
        CurrencyAPICurrenciesDTO dto = new CurrencyAPICurrenciesDTO(
                Map.of(
                        "FOO", new CurrencyAPICurrenciesDTO.CurrencyData("Foo Dollar", "*"),
                        "BAR", new CurrencyAPICurrenciesDTO.CurrencyData("Bar Lira", "@"),
                        "BAZ", new CurrencyAPICurrenciesDTO.CurrencyData("Baz Pound", "&")
                )
        );
        var mapper = new CurrencyAPICurrenciesDTOMapper();

        Set<Currency> expected = Set.of(
                new Currency("FOO", "Foo Dollar", "*"),
                new Currency("BAR", "Bar Lira", "@"),
                new Currency("BAZ", "Baz Pound", "&")
        );
        Set<Currency> actual = mapper.map(dto);

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
