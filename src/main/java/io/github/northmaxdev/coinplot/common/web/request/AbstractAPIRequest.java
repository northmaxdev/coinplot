// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.common.web.request;

import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.net.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public abstract class AbstractAPIRequest implements APIRequest {

    protected abstract @Nonnull HttpHost getHost();

    // Method getPathSegments is purposely not final: this is a default impl that you override if you need to
    protected @Nonnull List<String> getPathSegments() {
        return List.of();
    }

    // Method getParameters is purposely not final: this is a default impl that you override if you need to
    protected @Nonnull List<NameValuePair> getParameters() {
        return List.of();
    }

    @Override
    public final @Nonnull URI toURI() {
        try {
            return new URIBuilder()
                    .setHttpHost(getHost())
                    .setPathSegments(getPathSegments())
                    .setParameters(getParameters())
                    .build();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Produced malformed URI. Please check impl for syntax oversights.");
        }
    }
}
