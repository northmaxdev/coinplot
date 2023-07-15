// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

class FrankfurterCurrencySetRequestTests {

    @Test
    void actualURIEqualsExpected() {
        var request = new FrankfurterCurrencySetRequest();

        URI expected = URI.create("https://api.frankfurter.app/currencies");
        URI actual = request.toURI();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(FrankfurterCurrencySetRequest.class)
                .verify();
    }
}
