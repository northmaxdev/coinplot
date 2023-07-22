// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.currency;

import org.junit.jupiter.api.Test;

import static io.github.northmaxdev.coinplot.TestUtils.verifyJPAEntityEquals;

class CurrencyTests {

    @Test
    void eq() {
        verifyJPAEntityEquals(Currency.class);
    }
}
