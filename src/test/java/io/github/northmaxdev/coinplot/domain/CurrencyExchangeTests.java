// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class CurrencyExchangeTests {

    @Test
    void eq() {
        EqualsVerifier.forClass(CurrencyExchange.class);
    }
}
