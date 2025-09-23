// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyPairTests {

    @ParameterizedTest
    @CsvSource({
            "EUR,USD,0.0001",
            "USD,EUR,0.0001",
            "GBP,CHF,0.0001",
            "CHF,GBP,0.0001",
            "JPY,USD,0.01",
            "USD,JPY,0.01",
            "JPY,EUR,0.01",
            "EUR,JPY,0.01",
    })
    void returnsExpectedPipForPair(String baseCode, String quoteCode, String expectedPipAsString) {
        CurrencyPair currencyPair = CurrencyPair.fromIsoCodes(baseCode, quoteCode);
        BigDecimal expectedPip = new BigDecimal(expectedPipAsString);

        assertThat(currencyPair.getPip()).isEqualTo(expectedPip);
    }

    @Test
    void toStringReturnsExpectedFormat() {
        CurrencyPair euroToSwissFranc = CurrencyPair.fromIsoCodes("EUR", "CHF");

        assertThat(euroToSwissFranc.toString()).isEqualTo("EUR/CHF");
    }

    @Test
    void equalsAndHashCodeContract() {
        EqualsVerifier.forClass(CurrencyPair.class).verify();
    }
}