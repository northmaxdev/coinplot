// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static io.github.northmaxdev.coinplot.AssertUtils.assertAPIRequestURIEqualsExpected;

class FrankfurterCurrencySetRequestTests {

    @Test
    void reqURI() {
        assertAPIRequestURIEqualsExpected(
                "https://api.frankfurter.app/currencies",
                FrankfurterCurrencySetRequest::new
        );
    }

    @Test
    void eq() {
        EqualsVerifier.forClass(FrankfurterCurrencySetRequest.class)
                .verify();
    }
}
