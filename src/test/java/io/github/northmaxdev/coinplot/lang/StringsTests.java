// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
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
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    @DisplayName("blankToNull on null or blank values returns null")
    void blankToNullOnNullOrBlank(String s) {
        assertThat(Strings.blankToNull(s)).isNull();
    }

    @Test
    @DisplayName("blankToNull on a non-blank value returns it as-is")
    void blankToNullOnNonBlank() {
        assertThat(Strings.blankToNull("hi")).isEqualTo("hi");
    }
}
