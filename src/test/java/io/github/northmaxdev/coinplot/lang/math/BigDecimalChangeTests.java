// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class BigDecimalChangeTests {

    @Test
    void eq() {
        EqualsVerifier.forClass(BigDecimalChange.class)
                .verify();
    }
}