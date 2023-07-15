// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.currency;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyServiceTests {

    static final CurrencyService SERVICE = () -> Set.of(
            new Currency("FOO", "Foo Dollar"),
            new Currency("BAR", "Bar Dollar")
    );

    @Test
    void findsCurrencyOnKnownCode() {
        Optional<Currency> actual = SERVICE.getCurrency("FOO");

        assertThat(actual).contains(new Currency("FOO", "Foo Dollar"));
    }

    @Test
    void doesNotFindCurrencyOnUnknownCode() {
        Optional<Currency> actual = SERVICE.getCurrency("BAZ");

        assertThat(actual).isEmpty();
    }

    @Test
    void doesNotFindCurrencyOnNull() {
        Optional<Currency> actual = SERVICE.getCurrency(null);

        assertThat(actual).isEmpty();
    }
}
