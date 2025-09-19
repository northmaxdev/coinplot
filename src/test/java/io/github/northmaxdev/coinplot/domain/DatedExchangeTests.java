// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class DatedExchangeTests {

    @Test
    void equalsAndHashCodeContract() { // Critical
        EqualsVerifier.forClass(DatedExchange.class).verify();
    }
}
