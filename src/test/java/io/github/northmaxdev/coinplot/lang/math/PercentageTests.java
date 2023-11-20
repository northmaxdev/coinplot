// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PercentageTests {

    // The default MathContext(s) is/are subject to change as implementation detail(s).
    // Therefore, test against an explicitly declared custom one.
    private static final MathContext MATH_CONTEXT_FOR_TESTS = new MathContext(3, RoundingMode.HALF_UP);

    @Test
    void eq() {
        EqualsVerifier.forClass(Percentage.class);
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("ofChange calculates change percentage as expected")
    void correctlyCalculatesChange(BigDecimal before, BigDecimal after, BigDecimal expectedPercentageValue) {
        Percentage actual = Percentage.ofChange(before, after, MATH_CONTEXT_FOR_TESTS);

        assertThat(actual.getValue()).isEqualByComparingTo(expectedPercentageValue);
    }

    Stream<Arguments> correctlyCalculatesChange() {
        return Stream.of(
                arguments(BigDecimal.valueOf(2.5), BigDecimal.valueOf(3L), BigDecimal.valueOf(20L)),
                arguments(BigDecimal.valueOf(4L), BigDecimal.valueOf(3.5), BigDecimal.valueOf(-12.5)),
                arguments(BigDecimal.valueOf(5.96), BigDecimal.valueOf(5.96), BigDecimal.valueOf(0L)),
                arguments(BigDecimal.valueOf(0.5), BigDecimal.valueOf(-0.25), BigDecimal.valueOf(-150L)),
                arguments(BigDecimal.valueOf(2758L), BigDecimal.valueOf(0L), BigDecimal.valueOf(-100L)),
                arguments(BigDecimal.valueOf(-5L), BigDecimal.valueOf(2.5), BigDecimal.valueOf(150L)),
                arguments(BigDecimal.valueOf(-1L), BigDecimal.valueOf(-0.75), BigDecimal.valueOf(25L)),
                arguments(BigDecimal.valueOf(-0.375), BigDecimal.valueOf(-0.45), BigDecimal.valueOf(-20L)),
                arguments(BigDecimal.valueOf(-0.17), BigDecimal.valueOf(-0.17), BigDecimal.valueOf(0L)),
                arguments(BigDecimal.valueOf(-99999L), BigDecimal.valueOf(0L), BigDecimal.valueOf(100)),
                arguments(BigDecimal.valueOf(0L), BigDecimal.valueOf(0L), BigDecimal.valueOf(0L))
        );
    }
}
