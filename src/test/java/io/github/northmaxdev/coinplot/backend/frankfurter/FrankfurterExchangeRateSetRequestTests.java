// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.github.northmaxdev.coinplot.TestUtils.verifyAPIRequestEquals;

@Disabled // TODO
class FrankfurterExchangeRateSetRequestTests {

    @Test
    void reqURI() {
    }

    @Test
    void eq() {
        verifyAPIRequestEquals(FrankfurterExchangeRateSetRequest.class);
    }
}
