// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web.request;

import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.apache.hc.core5.http.URIScheme.HTTP;
import static org.apache.hc.core5.http.URIScheme.HTTPS;
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
                return new HttpHost(HTTPS.getId(), "example.com");
            }

            @Override
            protected Optional<String> getRootPathSegment() {
                return Optional.of("v2");
            }

            @Override
            protected @Nonnull String getEndpoint() {
                return "events";
            }

            @Override
            protected Optional<NameValuePair> getAccessKeyParameter() {
                return Optional.empty();
            }

            @Override
            protected List<NameValuePair> getAdditionalParameters() {
                return List.of();
            }
        };
        URI u1 = URI.create("https://example.com/v2/events");

        AbstractAPIRequest r2 = new AbstractAPIRequest() {
            @Override
            protected @Nonnull HttpHost getHost() {
                return new HttpHost(HTTP.getId(), "localhost", 8080);
            }

            @Override
            protected Optional<String> getRootPathSegment() {
                return Optional.empty();
            }

            @Override
            protected @Nonnull String getEndpoint() {
                return "stuff";
            }

            @Override
            protected Optional<NameValuePair> getAccessKeyParameter() {
                NameValuePair p = new BasicNameValuePair("key", "49f68a5c8493ec2c0bf489821c21fc3b");
                return Optional.of(p);
            }

            @Override
            protected List<NameValuePair> getAdditionalParameters() {
                NameValuePair p = new BasicNameValuePair("foo", "bar");
                return List.of(p);
            }
        };
        URI u2 = URI.create("http://localhost:8080/stuff?key=49f68a5c8493ec2c0bf489821c21fc3b&foo=bar");

        return Stream.of(
                arguments(r1, u1),
                arguments(r2, u2)
        );
    }
}
