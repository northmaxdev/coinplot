// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import io.github.northmaxdev.coinplot.backend.core.web.request.AbstractAPIRequest;
import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.NameValuePair;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.apache.hc.core5.http.URIScheme.HTTPS;

public abstract class AbstractEverapiAPIRequest extends AbstractAPIRequest {

    private static final HttpHost HOST = new HttpHost(HTTPS.getId(), "api.currencyapi.com");

    private final String accessKey;

    protected AbstractEverapiAPIRequest(@Nonnull String accessKey) {
        this.accessKey = accessKey;
    }

    // For subclasses' equals/hashCode overrides
    protected final String getAccessKey() {
        return accessKey;
    }

    @Override
    public final Map<String, String> headers() {
        // This can be cached as everything is immutable, but it's probably a very minor performance gain
        return Map.of("apikey", accessKey);
    }

    @Override
    protected final @Nonnull HttpHost getHost() {
        return HOST;
    }

    @Override
    protected final Optional<String> getRootPathSegment() {
        return Optional.of("v3");
    }

    @Override
    protected final Optional<NameValuePair> getAccessKeyParameter() {
        // The API *does* technically require an access key, but as a header - not as a parameter.
        return Optional.empty();
    }

    @Override
    public int hashCode() { // Deliberately non-final
        return Objects.hashCode(accessKey);
    }
}
