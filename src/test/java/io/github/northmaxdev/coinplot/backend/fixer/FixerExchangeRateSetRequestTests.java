// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class FixerExchangeRateSetRequestTests {

    @Test
    void actualURIEqualsExpected() {
        // TODO
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(FixerExchangeRateSetRequest.class)
                .verify();
    }
}
