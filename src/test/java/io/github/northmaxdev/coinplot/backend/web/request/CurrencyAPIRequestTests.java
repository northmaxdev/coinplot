// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.web.request;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyAPIRequestTests {

    @Test
    void actualURIEqualsExpected() {
        var request = new CurrencyAPIRequest();

        URI expected = URI.create("https://api.currencyapi.com");
        URI actual = request.toURI();

        assertThat(actual).isEqualTo(expected);
    }
}
