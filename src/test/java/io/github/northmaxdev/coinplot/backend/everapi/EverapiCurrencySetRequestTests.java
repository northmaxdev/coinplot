// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import org.junit.jupiter.api.Test;

import static io.github.northmaxdev.coinplot.TestUtils.assertExpectedHeadersContainActual;
import static io.github.northmaxdev.coinplot.TestUtils.assertExpectedURIsContainActual;
import static io.github.northmaxdev.coinplot.TestUtils.verifyAPIRequestEquals;
import static java.util.Map.entry;

class EverapiCurrencySetRequestTests {

    @Test
    void reqURI() {
        assertExpectedURIsContainActual(
                () -> new EverapiCurrencySetRequest("hi"),
                "https://api.currencyapi.com/v3/currencies"
        );
    }

    @Test
    void reqHeaders() {
        assertExpectedHeadersContainActual(
                () -> new EverapiCurrencySetRequest("5ac5355b84894ede056ab81b324c4675"),
                entry("apikey", "5ac5355b84894ede056ab81b324c4675")
        );
    }

    @Test
    void eq() {
        verifyAPIRequestEquals(EverapiCurrencySetRequest.class);
    }
}
