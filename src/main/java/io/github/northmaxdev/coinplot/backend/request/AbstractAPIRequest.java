// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.request;

import com.google.common.collect.ImmutableCollection;
import io.github.northmaxdev.coinplot.backend.currency.Currency;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public abstract class AbstractAPIRequest implements APIRequest {

    // IMPORTANT: Caching the URI implies that all outgoing subclasses MUST be immutable.
    private @Nullable URI cachedURI;

    protected AbstractAPIRequest() {
        this.cachedURI = null;
    }

    protected abstract @Nonnull HttpHost getHost();

    // Method getPathSegments is purposely not final: this is a default impl that you override if you need to
    protected @Nonnull List<String> getPathSegments() {
        return List.of();
    }

    // Method getParameters is purposely not final: this is a default impl that you override if you need to
    protected @Nonnull List<NameValuePair> getParameters() {
        return List.of();
    }

    // A convenient utility method for an operation that is frequently used by certain subclasses.
    protected static Optional<NameValuePair> joinCurrenciesToParameter(
            @Nonnull String parameterName,
            // ImmutableCollection is used instead of a regular Collection because:
            // 1. All fields representing a collection of currencies should be deeply immutable anyway
            // 2. ImmutableCollection makes strong guarantees about not allowing nulls
            @Nonnull ImmutableCollection<Currency> currencies) {
        if (currencies.isEmpty()) {
            return Optional.empty();
        }

        String joinedCodes = currencies.stream()
                .map(Currency::getThreeLetterISOCode)
                .collect(joining(","));

        NameValuePair parameter = new BasicNameValuePair(parameterName, joinedCodes);
        return Optional.of(parameter);
    }

    @Override
    public final @Nonnull URI toURI() {
        if (cachedURI == null) {
            try {
                cachedURI = new URIBuilder()
                        .setHttpHost(getHost())
                        .setPathSegments(getPathSegments())
                        .setParameters(getParameters())
                        .build();
            } catch (URISyntaxException e) {
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
