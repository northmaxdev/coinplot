// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.web.request;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

class FrankfurterRequestTests {

    @Test
    void actualURIEqualsExpected() {
        var request = new FrankfurterRequest();

        URI expected = URI.create("https://api.frankfurter.app");
        URI actual = request.toURI();

        assertThat(actual).isEqualTo(expected);
    }
}
