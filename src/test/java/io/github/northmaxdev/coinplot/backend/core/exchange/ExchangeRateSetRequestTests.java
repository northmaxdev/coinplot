// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ExchangeRateSetRequestTests {

    @Nested
    class ParametersBuilderTests {

        @Test
        void eq() {
            EqualsVerifier.forClass(ExchangeRateSetRequest.ParametersBuilder.class)
                    .suppress(Warning.NONFINAL_FIELDS)
                    .verify();
        }
    }
}
