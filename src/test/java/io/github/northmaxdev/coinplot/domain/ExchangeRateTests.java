// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assumptions.assumeThatCode;

class ExchangeRateTests {

    CurrencyPair mockedCurrencyPair = Mockito.mock(CurrencyPair.class);

    @Nested
    class CanonicalConstructor {

        @ParameterizedTest
        @ValueSource(ints = {-1, 0})
        void throwsIaeOnNonPositiveValue(int i) {
            BigDecimal nonPositiveValue = new BigDecimal(i);

            assertThatIllegalArgumentException().isThrownBy(() -> new ExchangeRate(mockedCurrencyPair, nonPositiveValue, Instant.EPOCH));
        }

        @Test
        void doesNotThrowOnPositiveValue() {
            assertThatNoException().isThrownBy(() -> new ExchangeRate(mockedCurrencyPair, BigDecimal.ONE, Instant.EPOCH));
        }
    }

    @Nested
    class InstanceMethods {

        @ParameterizedTest
        @CsvSource(textBlock = """
                82.62,10,826.2
                76.89,36,2768.04
                98.56,19.125,1884.96
                100.47,14,1406.58
                81.75,27.4,2239.95
                """)
        void applyProducesExpectedValue(BigDecimal exchangeRateValue, BigDecimal baseCurrencyAmount, BigDecimal expectedResult) {
            ExchangeRate exchangeRate = new ExchangeRate(mockedCurrencyPair, exchangeRateValue, Instant.EPOCH);

            assertThat(exchangeRate.apply(baseCurrencyAmount)).isEqualTo(expectedResult);
        }

        @Nested
        class EqualsContract {

            // We need to declare those as fields because you can't assign a value to a local variable of enclosing scope in a lambda
            CurrencyPair prefabCurrencyPair1;
            CurrencyPair prefabCurrencyPair2;

            @Test
            void test() {
                Currency euro = Currency.getInstance("EUR");
                Currency swissFranc = Currency.getInstance("CHF");
                Currency britishPound = Currency.getInstance("GBP");
                Currency canadianDollar = Currency.getInstance("CAD");

                assumeThatCode(() -> {
                    prefabCurrencyPair1 = new CurrencyPair(euro, swissFranc);
                    prefabCurrencyPair2 = new CurrencyPair(britishPound, canadianDollar);
                }).withFailMessage("cannot instantiate CurrencyPair prefabs (constructor throws)").doesNotThrowAnyException();

                EqualsVerifier.forClass(ExchangeRate.class)
                        .withPrefabValuesForField("currencyPair", prefabCurrencyPair1, prefabCurrencyPair2)
                        .withPrefabValues(BigDecimal.class, BigDecimal.ONE, BigDecimal.TWO)
                        .verify();
            }
        }
    }

    @Nested
    class StaticMethods {

    }
}
