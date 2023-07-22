// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import org.junit.jupiter.api.Test;

import static io.github.northmaxdev.coinplot.TestUtils.assertExpectedURIsContainActual;
import static io.github.northmaxdev.coinplot.TestUtils.verifyAPIRequestEquals;

class FrankfurterCurrencySetRequestTests {

    @Test
    void reqURI() {
        assertExpectedURIsContainActual(FrankfurterCurrencySetRequest::new, "https://api.frankfurter.app/currencies");
    }

    @Test
    void eq() {
        verifyAPIRequestEquals(FrankfurterCurrencySetRequest.class);
    }
}
