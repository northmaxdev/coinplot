// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

class FixerCurrencySetRequestTests {

    @Test
    void actualURIEqualsExpected() {
        var request = new FixerCurrencySetRequest("5ac5355b84894ede056ab81b324c4675");

        URI expected = URI.create("https://data.fixer.io/api/symbols?access_key=5ac5355b84894ede056ab81b324c4675");
        URI actual = request.toURI();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void equalsAndHashCode() {
        EqualsVerifier.forClass(FixerCurrencySetRequest.class)
                .withNonnullFields("accessKeyParameter")
                .verify();
    }
}
