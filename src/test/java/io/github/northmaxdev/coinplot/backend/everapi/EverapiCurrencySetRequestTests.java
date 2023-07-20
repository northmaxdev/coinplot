// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static io.github.northmaxdev.coinplot.AssertUtils.assertAPIRequestURIEqualsExpected;

class EverapiCurrencySetRequestTests {

    @Test
    void reqURI() {
        assertAPIRequestURIEqualsExpected(
                "https://api.currencyapi.com/v3/currencies",
                () -> new EverapiCurrencySetRequest("hi")
        );
    }

    @Test
    void eq() {
        EqualsVerifier.forClass(EverapiCurrencySetRequest.class)
                .verify();
    }
}
