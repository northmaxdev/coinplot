// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.SequencedCollection;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionUtilitiesTests {

    @Test
    @DisplayName("lastTwoElements correctly locates the last two elements")
    void lastTwoElementsOnTwoPlus() {
        SequencedCollection<Integer> collection = List.of(5, 1, 9, 4, 3);
        Pair<Integer, Integer> expected = Pair.of(4, 3);

        assertThat(CollectionUtilities.lastTwoElements(collection)).contains(expected);
    }

    @Test
    @DisplayName("lastTwoElements returns an empty Optional on a singleton collection")
    void lastTwoElementsOnSingleton() {
        SequencedCollection<BigInteger> collection = List.of(BigInteger.ONE);

        assertThat(CollectionUtilities.lastTwoElements(collection)).isEmpty();
    }

    @Test
    @DisplayName("lastTwoElements returns an empty Optional on an empty collection")
    void lastTwoElementsOnEmpty() {
        SequencedCollection<?> collection = List.of();

        assertThat(CollectionUtilities.lastTwoElements(collection)).isEmpty();
    }
}
