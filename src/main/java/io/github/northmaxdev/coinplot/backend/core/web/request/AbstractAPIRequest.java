// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web.request;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.net.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

public abstract class AbstractAPIRequest implements APIRequest {

    // IMPORTANT: Caching the URI implies that all outgoing subclasses MUST be immutable.
    private transient @Nullable URI cachedURI;

    protected AbstractAPIRequest() {
        this.cachedURI = null;
    }

    protected abstract @Nonnull HttpHost getHost();

    protected abstract Optional<String> getRootPathSegment();

    protected abstract @Nonnull String getEndpoint();

    protected abstract List<NameValuePair> getAdditionalParameters();

    @Override
    public final @Nonnull URI toURI() {
        if (cachedURI == null) {
            try {
                // Note: for some implementations, the endpoint is not a static string, but something that actually
                // requires computation(s), so it's recommended to call this method exactly once.
                String endpoint = getEndpoint();
                List<String> pathSegments = getRootPathSegment()
                        .map(pathRoot -> List.of(pathRoot, endpoint))
                        .orElse(List.of(endpoint));

                cachedURI = new URIBuilder()
                        .setHttpHost(getHost())
                        .setPathSegments(pathSegments)
                        .setParameters(getAdditionalParameters())
                        .build();
            } catch (URISyntaxException e) {
                // Should never happen unless an implementation accepts unvalidated direct user input
                throw new IllegalStateException("Produced malformed URI. Please check impl for syntax oversights.");
            }
        }
        return cachedURI;
    }

    @Override
    public final String toString() {
        // NOTE: When using Temurin JDK v17.0.7+7, which is the primary JDK for this project, the implementation of
        // URI::toString caches the string form and avoids excess computations. If this behavior is NOT explicitly
        // specified by the java.net.URI spec (and thus the aforementioned is a JDK-specific implementation detail),
        // then we'll have to cache the string form ourselves to ensure portability of optimizations.
        URI u = toURI();
        return u.toString();
    }
}
