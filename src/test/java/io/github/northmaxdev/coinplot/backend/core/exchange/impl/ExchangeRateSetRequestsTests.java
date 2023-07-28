// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange.impl;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ExchangeRateSetRequestsTests {

    @Nested
    class ParametersBuilderTests {

        @Test
        void eq() {
            EqualsVerifier.forClass(ExchangeRateSetRequests.ParametersBuilder.class)
                    .suppress(Warning.NONFINAL_FIELDS)
                    .verify();
        }
    }
}
