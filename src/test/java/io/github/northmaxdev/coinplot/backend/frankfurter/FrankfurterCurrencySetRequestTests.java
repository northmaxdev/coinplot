// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import org.junit.jupiter.api.Test;

import static io.github.northmaxdev.coinplot.TestUtils.assertAPIRequestURIEqualsExpected;
import static io.github.northmaxdev.coinplot.TestUtils.verifyAPIRequestEquals;

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
        verifyAPIRequestEquals(FrankfurterCurrencySetRequest.class);
    }
}
