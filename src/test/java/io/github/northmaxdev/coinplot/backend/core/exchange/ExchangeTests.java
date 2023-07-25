// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class ExchangeTests {

    @Test
    void eq() {
        EqualsVerifier.forClass(Exchange.class)
                .verify();
    }
}
