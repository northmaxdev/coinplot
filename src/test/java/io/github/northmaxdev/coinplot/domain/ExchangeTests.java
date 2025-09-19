// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class ExchangeTests {

    @Test
    void equalsAndHashCodeContract() { // Critical
        EqualsVerifier.forClass(Exchange.class).verify();
    }
}
