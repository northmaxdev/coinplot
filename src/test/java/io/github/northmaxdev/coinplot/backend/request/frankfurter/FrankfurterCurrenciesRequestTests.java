// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.request.frankfurter;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

class FrankfurterCurrenciesRequestTests {

    @Test
    void actualURIEqualsExpected() {
        var request = new FrankfurterCurrenciesRequest();

        URI actual = request.toURI();
        URI expected = URI.create("https://api.frankfurter.app/currencies");

        assertThat(actual).isEqualTo(expected);
    }
}
