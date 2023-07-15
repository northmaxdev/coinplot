// SPDX-License-Identifier: MIT

// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.__old;

import io.github.northmaxdev.coinplot.backend.__old.FrankfurterCurrenciesRequest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

class FrankfurterCurrenciesRequestTests {

    @Test
    void actualURIEqualsExpected() {
        var request = new FrankfurterCurrenciesRequest();

        URI actual = request.toURI();
        URI expected = URI.create("https://api.frankfurter.app/currencies");

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(FrankfurterCurrenciesRequest.class)
                .verify();
    }
}
