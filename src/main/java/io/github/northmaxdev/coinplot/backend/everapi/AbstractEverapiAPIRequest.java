// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import io.github.northmaxdev.coinplot.backend.core.web.request.AbstractAPIRequest;
import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.NameValuePair;

import java.util.Optional;

import static org.apache.hc.core5.http.URIScheme.HTTPS;

public abstract class AbstractEverapiAPIRequest extends AbstractAPIRequest {

    private static final HttpHost HOST = new HttpHost(HTTPS.getId(), "api.currencyapi.com");

    protected AbstractEverapiAPIRequest() {
        super();
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
        // The API technically requires an access key,
        // but it only accepts it as an HTTP header,
        // so we'll deal with it at the service level.
        return Optional.empty();
    }
}
