// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class ExchangeRateBatchTests {

    @Test
    void eq() {
        EqualsVerifier.forClass(ExchangeRateBatch.class)
                .verify();
    }
}
