// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class IntChangeTests {

    @Test
    void eq() {
        EqualsVerifier.forClass(IntChange.class)
                .verify();
    }
}
