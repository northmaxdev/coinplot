// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import org.junit.jupiter.api.Test;

import static io.github.northmaxdev.coinplot.TestUtils.assertAPIRequestURIEqualsExpected;
import static io.github.northmaxdev.coinplot.TestUtils.verifyAPIRequestEquals;

class FixerCurrencySetRequestTests {

    @Test
    void reqURI() {
        assertAPIRequestURIEqualsExpected(
                "https://data.fixer.io/api/symbols?access_key=5ac5355b84894ede056ab81b324c4675",
                () -> new FixerCurrencySetRequest("5ac5355b84894ede056ab81b324c4675")
        );
    }

    @Test
    void eq() {
        verifyAPIRequestEquals(FixerCurrencySetRequest.class);
    }
}
