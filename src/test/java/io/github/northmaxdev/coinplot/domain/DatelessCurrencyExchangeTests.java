// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class DatelessCurrencyExchangeTests {

    @Test
    void eq() {
        EqualsVerifier.forClass(DatelessCurrencyExchange.class).verify();
    }
}
