// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import io.github.northmaxdev.coinplot.backend.core.web.request.AbstractAPIRequest;
import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.HttpHost;

import java.util.Objects;
import java.util.Optional;

import static org.apache.hc.core5.http.URIScheme.HTTPS;

public abstract class AbstractEverapiAPIRequest extends AbstractAPIRequest {

    private static final HttpHost HOST = new HttpHost(HTTPS.getId(), "api.currencyapi.com");

    protected AbstractEverapiAPIRequest(@Nonnull String accessKey) {
        super(APIKey.asHeader("apikey", accessKey));
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
    public boolean equals(Object obj) { // Non-final
        return obj instanceof AbstractEverapiAPIRequest that
                && Objects.equals(this.getAccessKey(), that.getAccessKey());
    }
}
