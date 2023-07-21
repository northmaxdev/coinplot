// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.web.request.APIRequest;

import java.net.URI;
import java.util.Map;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public final class TestUtils {

    private TestUtils() {}

    ////////////////////
    // Currency stuff //
    ////////////////////

    public static final Currency FOO_DOLLAR = new Currency("FOO", "Foo Dollar");
    public static final Currency BAR_FRANC = new Currency("BAR", "Bar Franc");

    //////////////////////
    // APIRequest stuff //
    //////////////////////

    public static <R extends APIRequest> void assertAPIRequestURIEqualsExpected(
            String expectedURILiteral,
            Supplier<R> requestSupplier) {
        R request = requestSupplier.get();

        URI expected = URI.create(expectedURILiteral);
        URI actual = request.getURI();

        assertThat(actual).isEqualTo(expected);
    }

    public static <R extends APIRequest> void assertAPIRequestHeadersContainOnlyExpected(
            Map<String, String> expected,
            Supplier<R> requestSupplier) {
        R request = requestSupplier.get();

        Map<String, String> actual = request.getHeaders();

        assertThat(actual).containsExactlyInAnyOrderEntriesOf(expected);
    }
}