// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import org.junit.jupiter.api.Test;

import static io.github.northmaxdev.coinplot.Tests.assertExpectedURIsContainActual;

class FrankfurterCurrencySetRequestTests {

    @Test
    void reqURI() {
        assertExpectedURIsContainActual(new FrankfurterCurrencySetRequest(), "https://api.frankfurter.app/currencies");
    }
}
