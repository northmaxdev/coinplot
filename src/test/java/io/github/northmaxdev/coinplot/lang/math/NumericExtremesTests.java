// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.reverseOrder;
import static org.assertj.core.api.Assertions.assertThat;

class NumericExtremesTests {

    @Test
    void eq() {
        EqualsVerifier.forClass(NumericExtremes.class);
    }

    @Test
    @DisplayName("finds extremes with a custom comparator")
    void findWithCustomComparator() {
        Collection<BigDecimal> values = List.of(
                BigDecimal.valueOf(5L),
                BigDecimal.valueOf(2.4),
                BigDecimal.valueOf(10L),
                BigDecimal.ZERO,
                BigDecimal.valueOf(6L),
                BigDecimal.valueOf(-5L),
                BigDecimal.TWO
        );

        Optional<NumericExtremes> actual = NumericExtremes.find(values, reverseOrder());
        NumericExtremes expected = new NumericExtremes(
                // Since we use a reverse order,
                // the "min" property is the max value,
                // and the "max" property is the min value.
                BigDecimal.valueOf(10L),
                BigDecimal.valueOf(-5L)
        );

        assertThat(actual).contains(expected);
    }

    @Test
    @DisplayName("finds extremes with the default comparator")
    void findWithDefaultComparator() {
        Collection<BigDecimal> values = List.of(
                BigDecimal.valueOf(5L),
                BigDecimal.ZERO,
                BigDecimal.TWO,
                BigDecimal.valueOf(-5L),
                BigDecimal.valueOf(3.14),
                BigDecimal.valueOf(-2L)
        );

        Optional<NumericExtremes> actual = NumericExtremes.find(values);
        NumericExtremes expected = new NumericExtremes(
                BigDecimal.valueOf(-5L),
                BigDecimal.valueOf(5L)
        );

        assertThat(actual).contains(expected);
    }
}
