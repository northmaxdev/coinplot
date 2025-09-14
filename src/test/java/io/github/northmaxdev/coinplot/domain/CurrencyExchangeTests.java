// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CurrencyExchangeTests {

    @Test
    @DisplayName("equals/hashCode contract")
    void equalsAndHashCode() { // Critical
        EqualsVerifier.forClass(CurrencyExchange.class).verify();
    }
}
