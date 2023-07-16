// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.backend.core.web.request.AbstractAPIRequest;
import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.NameValuePair;

import java.util.Objects;
import java.util.Optional;

import static org.apache.hc.core5.http.URIScheme.HTTPS;

public abstract class AbstractFrankfurterAPIRequest extends AbstractAPIRequest {

    private static final HttpHost PUBLIC_INSTANCE_HOST = new HttpHost(HTTPS.getId(), "api.frankfurter.app");

    private final HttpHost host;

    protected AbstractFrankfurterAPIRequest() {
        this(PUBLIC_INSTANCE_HOST);
    }

    protected AbstractFrankfurterAPIRequest(@Nonnull HttpHost customHost) {
        this.host = customHost;
    }

    @Override
    protected final @Nonnull HttpHost getHost() {
        return host;
    }

    @Override
    protected final Optional<String> getRootPathSegment() {
        return Optional.empty();
    }

    @Override
    protected final Optional<NameValuePair> getAccessKeyParameter() {
        return Optional.empty();
    }

    @Override
    public int hashCode() { // Deliberately non-final
        return Objects.hashCode(host);
    }
}
