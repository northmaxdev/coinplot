// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import org.junit.jupiter.api.Test;

import static io.github.northmaxdev.coinplot.Tests.verifyJPAEntityEquals;

class ExchangeRateTests {

    @Test
    void eq() {
        verifyJPAEntityEquals(ExchangeRate.class);
    }
}
