// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.exchange;

import com.google.common.collect.ImmutableList;
import io.github.northmaxdev.coinplot.backend.currency.Currency;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.hc.core5.http.HttpHost;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FrankfurterExchangeRatesRequestTests {

    static final HttpHost DUMMY_HOST = new HttpHost("example.com");
    static final Currency FOO = new Currency("FOO", "Foo Dollar");
    static final Currency BAR = new Currency("BAR", "Bar Dollar");

    @Test
    void actualURIEqualsExpected() {
        LocalDate start = LocalDate.of(2000, Month.JANUARY, 1);
        LocalDate end = LocalDate.of(2000, Month.FEBRUARY, 1);
        var request = new FrankfurterExchangeRatesRequest(DUMMY_HOST, start, end, FOO, ImmutableList.of(BAR));

        URI actual = request.toURI();
        Iterable<URI> expectedURIs = List.of(
                URI.create("http://example.com/2000-01-01..2000-02-01?from=FOO&to=BAR"),
                URI.create("http://example.com/2000-01-01..2000-02-01?to=BAR&from=FOO")
        );

        assertThat(actual).isIn(expectedURIs);
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(FrankfurterExchangeRatesRequest.class)
                .verify();
    }
}
