// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.currency;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class CurrencyTests {

    @Test
    void equals() {
        EqualsVerifier.forClass(Currency.class)
                .suppress(Warning.SURROGATE_KEY)
                .verify();
    }
}
