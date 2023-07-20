// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.currency;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class CurrencyTests {

    @Test
    void eq() {
        EqualsVerifier.forClass(Currency.class)
                .suppress(Warning.SURROGATE_KEY) // To only compare entity IDs for equality
                .verify();
    }
}
