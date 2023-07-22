// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.github.northmaxdev.coinplot.TestUtils.verifyAPIRequestEquals;

@Disabled // TODO
class FixerExchangeRateSetRequestTests {

    @Test
    void reqURI() {
    }

    @Test
    void eq() {
        verifyAPIRequestEquals(FixerExchangeRateSetRequest.class);
    }
}
