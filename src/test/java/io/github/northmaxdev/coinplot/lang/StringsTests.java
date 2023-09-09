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
    @ValueSource(strings = {"  ", "\t\t", "\n"})
    @DisplayName("blankToOptional is empty on blank and null values")
    void blankToOptionalForBlankArg(String s) {
        Optional<String> actual = Strings.blankToOptional(s);

        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("blankToOptional returns a non-null, non-blank argument as-is")
    void blankToOptionalForNonBlankArg() {
        Optional<String> actual = Strings.blankToOptional("hi");

        assertThat(actual).contains("hi");
    }
}
