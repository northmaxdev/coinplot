// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot;

import io.github.northmaxdev.coinplot.backend.core.web.request.APIRequest;

import java.net.URI;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public final class AssertUtils {

    private AssertUtils() {}

    public static <R extends APIRequest> void assertAPIRequestURIEqualsExpected(
            String expectedURILiteral,
            Supplier<R> requestSupplier) {
        R request = requestSupplier.get();

        URI expected = URI.create(expectedURILiteral);
        URI actual = request.getURI();

        assertThat(actual).isEqualTo(expected);
    }
}
