// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MoreCollectionsTests {

    @Test
    @DisplayName("isNotEmptyAndWithoutNulls returns true on a non-empty collection without nulls")
    void isNotEmptyAndWithoutNullsTrue() {
        Collection<?> collection = List.of("five", 5, 5.0);

        assertThat(MoreCollections.isNotEmptyAndWithoutNulls(collection)).isTrue();
    }

    @ParameterizedTest
    @EmptySource
    @MethodSource
    @DisplayName("isNotEmptyAndWithoutNulls returns false on a given collection")
    void isNotEmptyAndWithoutNullsFalse(List<?> list) { // @EmptySource does not support Collection
        assertThat(MoreCollections.isNotEmptyAndWithoutNulls(list)).isFalse();
    }

    Stream<Arguments> isNotEmptyAndWithoutNullsFalse() {
        // ArrayList permits null elements

        Collection<?> onlyNulls = new ArrayList<>(2);
        onlyNulls.add(null);
        onlyNulls.add(null);

        Collection<Object> fiftyFifty = new ArrayList<>(2);
        fiftyFifty.add(10);
        fiftyFifty.add(null);

        return Stream.of(
                arguments(onlyNulls),
                arguments(fiftyFifty)
        );
    }
}
