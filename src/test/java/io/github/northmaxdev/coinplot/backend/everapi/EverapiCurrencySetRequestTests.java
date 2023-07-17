// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

class EverapiCurrencySetRequestTests {

    @Test
    void actualURIEqualsExpected() {
        var request = new EverapiCurrencySetRequest("hi");

        URI expected = URI.create("https://api.currencyapi.com/v3/currencies");
        URI actual = request.getURI();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(EverapiCurrencySetRequest.class)
                .verify();
    }
}
