// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyPairTests {

    @Nested
    class Involves {

        @Test
        void returnsTrueOnInvolvedBase() {
            CurrencyPair pair = CurrencyPair.fromIsoCodes("EUR", "USD");
            Currency euro = Currency.getInstance("EUR");

            assertThat(pair.involves(euro)).isTrue();
        }

        @Test
        void returnsTrueOnInvolvedQuote() {
            CurrencyPair pair = CurrencyPair.fromIsoCodes("EUR", "USD");
            Currency usDollar = Currency.getInstance("USD");

            assertThat(pair.involves(usDollar)).isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {"JPY", "CHF", "GBP", "KRW", "CAD", "AUD"})
        void returnsFalseOnUninvolvedCurrency(String currencyCode) {
            CurrencyPair pair = CurrencyPair.fromIsoCodes("EUR", "USD");
            Currency currency = Currency.getInstance(currencyCode);

            assertThat(pair.involves(currency)).isFalse();
        }

        @Test
        void returnsFalseOnNull() {
            CurrencyPair pair = CurrencyPair.fromIsoCodes("EUR", "USD");

            assertThat(pair.involves(null)).isFalse();
        }
    }

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
    void returnsExpectedPipDefinitionForPair(String baseCode, String quoteCode, String expectedPipAsString) {
        CurrencyPair currencyPair = CurrencyPair.fromIsoCodes(baseCode, quoteCode);
        BigDecimal expectedPip = new BigDecimal(expectedPipAsString);

        assertThat(currencyPair.getPipDefinition()).isEqualTo(expectedPip);
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