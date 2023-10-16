// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PercentageTests {

    @ParameterizedTest
    @MethodSource
    @DisplayName("ofChange calculates change percentage as expected")
    void correctlyCalculatesChange(double before, double after, double expectedPercentageValue) {
        Percentage actual = Percentage.ofChange(before, after);

        assertThat(actual)
                .extracting(Percentage::value, InstanceOfAssertFactories.DOUBLE)
                .isEqualTo(expectedPercentageValue, offset(0.0001)); // This should be enough for percentages
    }

    Stream<Arguments> correctlyCalculatesChange() {
        return Stream.of(
                arguments(2.5, 3d, 20d),
                arguments(4d, 3.5, -12.5),
                arguments(5.96, 5.96, 0d),
                arguments(0.5, -0.25, -150d),
                arguments(2758d, 0d, -100d),
                arguments(-5d, 2.5, 150d),
                arguments(-1d, -0.75, 25d),
                arguments(-0.375, -0.45, -20d),
                arguments(-0.17, -0.17, 0d),
                arguments(-99999d, 0d, 100d),
                arguments(0d, 0d, 0d)
        );
    }
}
