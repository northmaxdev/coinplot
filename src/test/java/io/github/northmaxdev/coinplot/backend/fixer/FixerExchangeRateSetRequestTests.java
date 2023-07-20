// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled // TODO
class FixerExchangeRateSetRequestTests {

    @Test
    void reqURI() {
    }

    @Test
    void eq() {
        EqualsVerifier.forClass(FixerExchangeRateSetRequest.class)
                .verify();
    }
}
