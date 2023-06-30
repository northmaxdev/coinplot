// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.exchange;

import com.google.common.collect.ImmutableList;
import io.github.northmaxdev.coinplot.backend.currency.Currency;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CurrencyAPIExchangeRatesRequestTests {

    static final Currency FOO = new Currency("FOO", "Foo Dollar");

    @Test
    void actualURIEqualsExpected() {
        Instant start = LocalDateTime.of(2000, Month.JANUARY, 1, 15, 30)
                .toInstant(ZoneOffset.UTC);
        Instant end = LocalDateTime.of(2000, Month.FEBRUARY, 1, 12, 45)
                .toInstant(ZoneOffset.UTC);
        var request = new CurrencyAPIExchangeRatesRequest(start, end, FOO, ImmutableList.of());

        URI actual = request.toURI();
        Iterable<URI> expectedURIs = List.of(
                URI.create("https://api.currencyapi.com/v3/range?datetime_start=2000-01-01T15%3A30%3A00Z&datetime_end=2000-02-01T12%3A45%3A00Z&base_currency=FOO"),
                URI.create("https://api.currencyapi.com/v3/range?datetime_start=2000-01-01T15%3A30%3A00Z&base_currency=FOO&datetime_end=2000-02-01T12%3A45%3A00Z"),
                URI.create("https://api.currencyapi.com/v3/range?datetime_end=2000-02-01T12%3A45%3A00Z&datetime_start=2000-01-01T15%3A30%3A00Z&base_currency=FOO"),
                URI.create("https://api.currencyapi.com/v3/range?datetime_end=2000-02-01T12%3A45%3A00Z&base_currency=FOO&datetime_start=2000-01-01T15%3A30%3A00Z"),
                URI.create("https://api.currencyapi.com/v3/range?base_currency=FOO&datetime_start=2000-01-01T15%3A30%3A00Z&datetime_end=2000-02-01T12%3A45%3A00Z"),
                URI.create("https://api.currencyapi.com/v3/range?base_currency=FOO&datetime_end=2000-02-01T12%3A45%3A00Z&datetime_start=2000-01-01T15%3A30%3A00Z")
        );

        assertThat(actual).isIn(expectedURIs);
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(CurrencyAPIExchangeRatesRequest.class)
                .verify();
    }
}
