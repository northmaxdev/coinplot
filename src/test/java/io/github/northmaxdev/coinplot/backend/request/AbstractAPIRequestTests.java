// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.request;

import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URI;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class AbstractAPIRequestTests {

    @ParameterizedTest
    @MethodSource("provideRequestAndURIPairs")
    void actualURIEqualsExpected(AbstractAPIRequest request, URI expected) {
        URI actual = request.toURI();

        assertThat(actual).isEqualTo(expected);
    }

    Stream<Arguments> provideRequestAndURIPairs() {
        AbstractAPIRequest r1 = new AbstractAPIRequest() {
            @Override
            protected @Nonnull HttpHost getHost() {
                return new HttpHost("https", "example.com");
            }
            @Override
            protected @Nonnull List<String> getPathSegments() {
                return List.of("v2", "events");
            }
        };
        URI u1 = URI.create("https://example.com/v2/events");

        AbstractAPIRequest r2 = new AbstractAPIRequest() {
            @Override
            protected @Nonnull HttpHost getHost() {
                return new HttpHost("localhost", 8080);
            }
            @Override
            protected @Nonnull List<String> getPathSegments() {
                return List.of("stuff");
            }
            @Override
            protected @Nonnull List<NameValuePair> getParameters() {
                return List.of(
                        new BasicNameValuePair("foo", "bar"),
                        new BasicNameValuePair("baz", "5")
                );
            }
        };
        URI u2 = URI.create("http://localhost:8080/stuff?foo=bar&baz=5");

        return Stream.of(
                arguments(r1, u1),
                arguments(r2, u2)
        );
    }
}
