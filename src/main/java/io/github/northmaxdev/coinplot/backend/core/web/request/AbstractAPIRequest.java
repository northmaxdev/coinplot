// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web.request;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
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

    //////////////////////////////////////
    // Inner types (AbstractAPIRequest) //
    //////////////////////////////////////

    protected record AccessKey(@Nonnull String name,
                               @Nonnull String value,
                               @Nonnull SpecificationStrategy specificationStrategy) {

        /////////////////////////////
        // Inner types (AccessKey) //
        /////////////////////////////

        public enum SpecificationStrategy {
            AS_QUERY_PARAMETER,
            AS_HEADER
        }

        ///////////////////////////////
        // Instantiation (AccessKey) //
        ///////////////////////////////

        // Subclass must supply known-to-be-valid String values.
        public AccessKey {
            Objects.requireNonNull(name, "Access key name cannot be null");
            Objects.requireNonNull(value, "Access key value cannot be null");
            Objects.requireNonNull(specificationStrategy, "Access key specification strategy cannot be null");
        }

        public static @Nonnull AccessKey asQueryParameter(@Nonnull String name, @Nonnull String value) {
            return new AccessKey(name, value, SpecificationStrategy.AS_QUERY_PARAMETER);
        }

        public static @Nonnull AccessKey asHeader(@Nonnull String name, @Nonnull String value) {
            return new AccessKey(name, value, SpecificationStrategy.AS_HEADER);
        }

        /////////////////////////
        // Getters (AccessKey) //
        /////////////////////////

        public boolean isSpecifiedAsQueryParameter() {
            return specificationStrategy == SpecificationStrategy.AS_QUERY_PARAMETER;
        }

        public boolean isSpecifiedAsHeader() {
            return specificationStrategy == SpecificationStrategy.AS_HEADER;
        }

        //////////////////////////////////////////
        // equals/hashCode/toString (AccessKey) //
        //////////////////////////////////////////

        @Override
        public String toString() {
            return value;
        }
    }

    /////////////////////////////////
    // Fields (AbstractAPIRequest) //
    /////////////////////////////////

    private final @Nullable AccessKey accessKey;

    ////////////////////////////////////////
    // Instantiation (AbstractAPIRequest) //
    ////////////////////////////////////////

    protected AbstractAPIRequest() {
        this(null);
    }

    protected AbstractAPIRequest(@Nullable AccessKey accessKey) {
        this.accessKey = accessKey;
    }

    //////////////////////////////
    // Class Hierarchy API: URI //
    //////////////////////////////

    protected abstract @Nonnull HttpHost getHost();

    // Deliberately non-final, merely a default implementation.
    // Subclass must supply a known-to-be-valid String value.
    protected Optional<String> getRootPathSegment() {
        return Optional.empty();
    }

    // Subclass must supply a known-to-be-valid String value.
    protected abstract @Nonnull String getEndpoint();

    // Deliberately non-final, merely a default implementation.
    // Subclass must supply known-to-be-valid String values.
    protected @Nonnull Map<String, String> getParameters() {
        return Map.of();
    }

    @Override
    public final @Nonnull URI getURI() {
        try {
            // Ideally, each one of those abstract methods above should be called
            // exactly once, as they may or may not include expensive computations.

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
            if (accessKey != null && accessKey.isSpecifiedAsQueryParameter()) {
                builder.setParameter(accessKey.name(), accessKey.value());
            }

            return builder.build();
        } catch (URISyntaxException e) {
            // As the message suggests, this should never happen
            throw new IllegalStateException("Produced a malformed URI, please check subclass implementation for developer mistakes!");
        }
    }

    //////////////////////////////////
    // Class Hierarchy API: Headers //
    //////////////////////////////////

    // Deliberately non-final, merely a default implementation.
    // Subclass must supply known-to-be-valid String values.
    protected @Nonnull Map<String, String> getHeadersExcludingAccessKey() {
        return Map.of();
    }

    @Override
    public final @Nonnull Map<String, String> getHeaders() {
        Map<String, String> headersWithoutKey = getHeadersExcludingAccessKey();

        if (accessKey != null && accessKey.isSpecifiedAsHeader()) {
            Map<String, String> headersWithKey = new HashMap<>(headersWithoutKey);
            headersWithKey.put(accessKey.name(), accessKey.value());
            return headersWithKey;
        }

        return headersWithoutKey;
    }

    //////////////////////////////////
    // Getters (AbstractAPIRequest) //
    //////////////////////////////////

    // Needed primarily for subclass' equals/hashCode
    protected final Optional<AccessKey> getAccessKey() {
        return Optional.ofNullable(accessKey);
    }

    ///////////////////////////////////////////////////
    // equals/hashCode/toString (AbstractAPIRequest) //
    ///////////////////////////////////////////////////

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
