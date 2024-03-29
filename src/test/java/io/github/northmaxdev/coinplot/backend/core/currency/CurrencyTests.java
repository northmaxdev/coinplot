// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.currency;

import org.junit.jupiter.api.Test;

import static io.github.northmaxdev.coinplot.Tests.verifyJPAEntityEquals;

class CurrencyTests {

    @Test
    void eq() {
        verifyJPAEntityEquals(Currency.class);
    }
}
