// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.currency;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyAPICurrenciesRequestTests {

    @Test
    void actualURIEqualsExpected() {
        var request = new CurrencyAPICurrenciesRequest();

        URI actual = request.toURI();
        URI expected = URI.create("https://api.currencyapi.com/v3/currencies");

        assertThat(actual).isEqualTo(expected);
    }
}
