// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.common.chrono;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class LocalDateRangeTests {

    @Nested
    class Constructor {

        @Test
        void throwsIAEIfEndBeforeStart() {
            assertThatIllegalArgumentException().isThrownBy(() -> {
                var start = LocalDate.of(2000, 1, 3);
                var end = LocalDate.of(2000, 1, 2);
                new LocalDateRange(start, end);
            });
        }

        @Test
        void throwsIAEIfEndEqualsStart() {
            assertThatIllegalArgumentException().isThrownBy(() -> {
                var start = LocalDate.of(2000, 1, 1);
                var end = LocalDate.of(2000, 1, 1);
                new LocalDateRange(start, end);
            });
        }

        @Test
        void doesNotThrowIfEndAfterStart() {
            assertThatNoException().isThrownBy(() -> {
                var start = LocalDate.of(2000, 1, 2);
                var end = LocalDate.of(2000, 1, 3);
                new LocalDateRange(start, end);
            });
        }
    }

    @Nested
    class RangeCheck {

        @ParameterizedTest
        @MethodSource("provideParamsAndExpectedValues")
        void paramsReturnExpectedValue(LocalDate start, LocalDate end, boolean expectedValue) {
            assertThat(LocalDateRange.isValid(start, end)).isEqualTo(expectedValue);
        }

        Stream<Arguments> provideParamsAndExpectedValues() {
            return Stream.of(
                    arguments(LocalDate.EPOCH, null, false),
                    arguments(null, LocalDate.EPOCH, false),
                    arguments(null, null, false),
                    arguments(LocalDate.EPOCH, LocalDate.EPOCH, false),
                    arguments(LocalDate.MAX, LocalDate.MIN, false),
                    arguments(LocalDate.MIN, LocalDate.MAX, true)
            );
        }
    }
}
