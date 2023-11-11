// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class BigDecimalsTests {

    @Test
    @DisplayName("equalIgnoringScale returns true on instances of equal values and different scales")
    void equalIgnoringScaleTrue() {
        BigDecimal x = BigDecimal.valueOf(2L);
        BigDecimal y = BigDecimal.valueOf(2.00);

        assertThat(BigDecimals.equalIgnoringScale(x, y)).isTrue();
    }

    @Test
    @DisplayName("equalIgnoringScale returns false on instances of different values")
    void equalIgnoringScaleFalse() {
        BigDecimal x = BigDecimal.valueOf(2.5);
        BigDecimal y = BigDecimal.valueOf(2.500000001);

        assertThat(BigDecimals.equalIgnoringScale(x, y)).isFalse();
    }

    @Test
    @DisplayName("hashIgnoringScale produces equal integers on instances of equal values and different scales")
    void hashIgnoringScale() {
        BigDecimal x = BigDecimal.valueOf(1L);
        BigDecimal y = BigDecimal.valueOf(1.000);

        int hc1 = BigDecimals.hashIgnoringScale(x);
        int hc2 = BigDecimals.hashIgnoringScale(y);

        assertThat(hc1).isEqualTo(hc2);
    }

    // Object::hashCode contract (as of Java 21):
    // "It is not required that if two objects are unequal according to the equals method,
    // then calling the hashCode method on each of the two objects must produce distinct integer results."
    // Therefore, no tests for hashIgnoringScale inequality cases.
}
