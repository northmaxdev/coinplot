// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web.request;

import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.net.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractAPIRequest implements APIRequest {

    //////////////////
    //    Fields    //
    //////////////////

    private final APIKey accessKey;
    // TODO:
    //  Lazy-compute and cache both URI and headers.
    //  Let readers know that in that case all subclasses must be deeply immutable to maintain integrity.

    ////////////////////////
    //    Constructors    //
    ////////////////////////

    protected AbstractAPIRequest() {
        this(APIKey.none());
    }

    protected AbstractAPIRequest(@Nonnull APIKey accessKey) {
        this.accessKey = accessKey;
    }

    ////////////////////////////////////
    //    Class Hierarchy API: URI    //
    ////////////////////////////////////

    protected abstract @Nonnull HttpHost getHost();

    // Deliberately non-final, merely a default impl
    protected Optional<String> getRootPathSegment() {
        return Optional.empty();
    }

    protected abstract @Nonnull String getEndpoint();

    // Deliberately non-final, merely a default impl
    protected Map<String, String> getParameters() {
        return Map.of();
    }

    @Override
    public final @Nonnull URI getURI() {
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

            Map<String, String> parameters = getParameters();
            parameters.forEach(builder::setParameter);
            if (accessKey.isSpecifiedAsQueryParameter()) {
                builder.setParameter(accessKey.name(), accessKey.value());
            }

            return builder.build();
        } catch (URISyntaxException e) {
            // Should never happen unless an implementation accepts unvalidated direct user input
            throw new IllegalStateException("Produced malformed URI. Please check impl for syntax oversights.");
        }
    }

    ////////////////////////////////////////
    //    Class Hierarchy API: Headers    //
    ////////////////////////////////////////

    // Deliberately non-final, merely a default impl
    protected Map<String, String> getHeadersExcludingAPIKey() {
        return Map.of();
    }

    @Override
    public final Map<String, String> getHeaders() {
        Map<String, String> headersWithoutKey = getHeadersExcludingAPIKey();

        if (accessKey.isSpecifiedAsHeader()) {
            Map<String, String> headersWithKey = new HashMap<>(headersWithoutKey);
            headersWithKey.put(accessKey.name(), accessKey.value());
            return headersWithKey;
        }

        return headersWithoutKey;
    }

    ///////////////////////////////
    //    Standard Java stuff    //
    ///////////////////////////////

    protected final APIKey getAccessKey() {
        return accessKey;
    }

    @Override
    public boolean equals(Object obj) { // Non-final
        return obj instanceof AbstractAPIRequest that
                && Objects.equals(this.accessKey, that.accessKey);
    }

    @Override
    public int hashCode() { // Non-final
        return Objects.hashCode(accessKey);
    }

    @Override
    public final String toString() {
        URI uri = getURI();
        Map<String, String> headers = getHeaders();

        StringBuilder sb = new StringBuilder()
                .append("[URI: ")
                .append(uri);

        if (!headers.isEmpty()) {
            sb.append(" Headers: ");
            sb.append(headers);
        }

        sb.append(']');
        return sb.toString();
    }
}
