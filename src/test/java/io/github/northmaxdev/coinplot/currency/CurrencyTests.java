// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.currency;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class CurrencyTests {

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(Currency.class)
                .suppress(Warning.SURROGATE_KEY)
                .verify();
    }
}
