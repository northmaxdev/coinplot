// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class EverapiExchangeRateSetRequestTests {

    @Test
    void actualURIEqualsExpected() {
        // TODO
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(EverapiExchangeRateSetRequest.class)
                .verify();
    }
}
