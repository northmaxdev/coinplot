// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web.request;

import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.HttpHost;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URI;
import java.util.stream.Stream;

import static org.apache.hc.core5.http.URIScheme.HTTP;
import static org.apache.hc.core5.http.URIScheme.HTTPS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class AbstractAPIRequestTests {

    @ParameterizedTest
    @MethodSource("provideRequestAndURIPairs")
    void actualURIEqualsExpected(AbstractAPIRequest request, URI expected) {
        URI actual = request.getURI();

        assertThat(actual).isEqualTo(expected);
    }

    Stream<Arguments> provideRequestAndURIPairs() {
        AbstractAPIRequest r1 = new AbstractAPIRequest() {
            @Override
            protected @Nonnull HttpHost getHost() {
                return new HttpHost(HTTP.getId(), "localhost", 8080);
            }
            @Override
            protected @Nonnull String getEndpoint() {
                return "hello";
            }
        };
        URI u1 = URI.create("http://localhost:8080/hello");

        AbstractAPIRequest r2 = new AbstractAPIRequest(APIKey.asQueryParameter("foo", "bar")) {
            @Override
            protected @Nonnull HttpHost getHost() {
                return new HttpHost(HTTPS.getId(), "example.com");
            }
            @Override
            protected @Nonnull String getEndpoint() {
                return "test";
            }
        };
        URI u2 = URI.create("https://example.com/test?foo=bar");

        return Stream.of(
                arguments(r1, u1),
                arguments(r2, u2)
        );
    }
}
