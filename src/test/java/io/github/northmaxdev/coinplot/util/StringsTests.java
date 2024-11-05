// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class StringsTests {

    @Test
    @DisplayName("substringBefore result contains the expected value")
    void substringBeforePresent() {
        Optional<String> substring = Strings.substringBefore("Hello world!", " world!");

        assertThat(substring).contains("Hello");
    }

    @Test
    @DisplayName("substringBefore value is absent")
    void substringBeforeAbsent() {
        Optional<String> substring = Strings.substringBefore("Hello world!", "hi");

        assertThat(substring).isEmpty();
    }
}
