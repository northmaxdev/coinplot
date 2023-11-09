// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class BigDecimalsTests {

    @Test
    @DisplayName("equalIgnoringScale returns true on instances of equals values and different scales")
    void equalIgnoringScaleTrue() {
        BigDecimal x = BigDecimal.valueOf(0L);
        BigDecimal y = BigDecimal.valueOf(0.000);

        assertThat(BigDecimals.equalIgnoringScale(x, y)).isTrue();
    }
}
