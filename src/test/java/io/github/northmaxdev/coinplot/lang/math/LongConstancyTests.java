// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class LongConstancyTests {

    @Test
    void eq() {
        EqualsVerifier.forClass(LongConstancy.class)
                .verify();
    }
}
