// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled // TODO
class EverapiExchangeRateSetRequestTests {

    @Test
    void reqURI() {
    }

    @Test
    void eq() {
        EqualsVerifier.forClass(EverapiExchangeRateSetRequest.class)
                .verify();
    }
}
