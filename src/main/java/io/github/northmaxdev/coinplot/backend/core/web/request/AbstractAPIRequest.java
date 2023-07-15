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

    protected abstract Optional<NameValuePair> getAccessKeyParameter();

    protected abstract List<NameValuePair> getAdditionalParameters();

    @Override
    public final @Nonnull URI toURI() {
        if (cachedURI == null) {
            try {
                // Ideally, each one of those abstract methods above should be called exactly once,
                // as they may or may not include expensive computations.

                URIBuilder builder = new URIBuilder();
                HttpHost host = getHost();
                builder.setHttpHost(host);

                String endpoint = getEndpoint();
                List<String> pathSegments = getRootPathSegment()
                        .map(root -> List.of(root, endpoint))
                        .orElse(List.of(endpoint));
                builder.setPathSegments(pathSegments);

                Optional<NameValuePair> accessKey = getAccessKeyParameter();
                accessKey.ifPresent(builder::addParameter);

                List<NameValuePair> additionalParameters = getAdditionalParameters();
                builder.addParameters(additionalParameters);

                cachedURI = builder.build();
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
