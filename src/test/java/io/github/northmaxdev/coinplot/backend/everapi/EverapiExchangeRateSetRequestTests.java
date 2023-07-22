// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.github.northmaxdev.coinplot.TestUtils.verifyAPIRequestEquals;

@Disabled // TODO
class EverapiExchangeRateSetRequestTests {

    @Test
    void reqURI() {
    }

    @Test
    void eq() {
        verifyAPIRequestEquals(EverapiExchangeRateSetRequest.class);
    }
}
