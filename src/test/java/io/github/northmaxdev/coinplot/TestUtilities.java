// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.web.request.APIRequest;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public final class TestUtilities {

    private TestUtilities() {
        throw new UnsupportedOperationException();
    }

    ////////////////////
    // Currency stuff //
    ////////////////////

    public static final Currency FOO_DOLLAR = new Currency("FOO", "Foo Dollar");
    public static final Currency BAR_FRANC = new Currency("BAR", "Bar Franc");
    public static final Currency BAZ_POUND = new Currency("BAZ", "Baz Pound");

    //////////////////////
    // APIRequest stuff //
    //////////////////////

    public static <R extends APIRequest> void assertExpectedURIsContainActual(
            Supplier<R> requestSupplier,
            String... possibleOutcomes) {
        R request = requestSupplier.get();

        Iterable<URI> expected = Arrays.stream(possibleOutcomes)
                .map(URI::create)
                .toList();
        URI actual = request.getURI();

        assertThat(actual).isIn(expected);
    }

    @SafeVarargs
    public static <R extends APIRequest> void assertExpectedHeadersContainActual(
            Supplier<R> requestSupplier,
            Map.Entry<String, String>... expectedHeaders) {
        R request = requestSupplier.get();

        Map<String, String> expected = Map.ofEntries(expectedHeaders);
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

    ///////////////////////
    // Persistence stuff //
    ///////////////////////

    public static <T> void verifyJPAEntityEquals(Class<T> entityClass) {
        EqualsVerifier.forClass(entityClass)
                .suppress(Warning.SURROGATE_KEY) // To only compare entity IDs for equality
                .verify();
    }
}
