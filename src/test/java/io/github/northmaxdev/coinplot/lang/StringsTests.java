// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t\t", "\n"})
    @DisplayName("blankToNull returns null on blank values")
    void blankToNullForBlankArg(String s) {
        String actual = Strings.blankToNull(s);

        assertThat(actual).isNull();
    }

    @Test
    @DisplayName("blankToNull returns a non-blank argument as-is")
    void blankToNullForNonBlankArg() {
        String actual = Strings.blankToNull("hi");

        assertThat(actual).isEqualTo("hi");
    }
}
