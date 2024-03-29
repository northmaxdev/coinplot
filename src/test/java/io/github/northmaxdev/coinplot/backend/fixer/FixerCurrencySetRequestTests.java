// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import org.junit.jupiter.api.Test;

import static io.github.northmaxdev.coinplot.Tests.assertExpectedURIsContainActual;

class FixerCurrencySetRequestTests {

    @Test
    void reqURI() {
        assertExpectedURIsContainActual(
                new FixerCurrencySetRequest("5ac5355b84894ede056ab81b324c4675"),
                "https://data.fixer.io/api/symbols?access_key=5ac5355b84894ede056ab81b324c4675"
        );
    }
}
