// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.web.request.APIRequest;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

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

    public static <R extends APIRequest> void verifyAPIRequestEquals(Class<R> requestClass) {
        EqualsVerifier.forClass(requestClass)
                // The class hierarchy of APIRequest implementations mixes between subclasses that add state and
                // subclasses that merely add behavior. Running an EqualsVerifier with standard configuration leads to
                // "subclass does not equal superclass" errors. Configuring withRedefinedSuperclass() leads to "subclass
                // equals superclass but should not" errors. The combination of the former and latter is very weird
                // considering that we're talking about the same equals/hashCode implementations being tested.
                // Suppressing such strict inheritance checks passes the tests, but I'm not sure if this is actually
                // appropriate for the issue at hand considering its documentation, so this may or may not lead to
                // subtle bugs later on (hopefully not). In the meantime, the tested equals/hashCode implementations
                // look completely OK.
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }
}
