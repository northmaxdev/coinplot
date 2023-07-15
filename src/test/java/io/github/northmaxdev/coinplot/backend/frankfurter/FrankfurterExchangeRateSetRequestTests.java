// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class FrankfurterExchangeRateSetRequestTests {

    @Test
    void actualURIEqualsExpected() {
        // TODO
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(FrankfurterExchangeRateSetRequest.class)
                .verify();
    }
}
