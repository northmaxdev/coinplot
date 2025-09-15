// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExchangeTests {

    @Test
    @DisplayName("equals/hashCode contract")
    void equalsAndHashCode() { // Critical
        EqualsVerifier.forClass(Exchange.class).verify();
    }
}
