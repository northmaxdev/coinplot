// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled // TODO
class FrankfurterExchangeRateSetRequestTests {

    @Test
    void reqURI() {
    }

    @Test
    void eq() {
        EqualsVerifier.forClass(FrankfurterExchangeRateSetRequest.class)
                .verify();
    }
}
