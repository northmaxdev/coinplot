// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class FixerCurrencySetRequestTests {

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(FixerCurrencySetRequest.class)
                .verify();
    }
}
