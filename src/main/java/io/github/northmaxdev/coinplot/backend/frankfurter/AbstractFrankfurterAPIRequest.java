// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.backend.core.web.request.AbstractAPIRequest;
import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.HttpHost;

import java.util.Objects;

import static org.apache.hc.core5.http.URIScheme.HTTPS;

public abstract class AbstractFrankfurterAPIRequest extends AbstractAPIRequest {

    private static final HttpHost PUBLIC_INSTANCE_HOST = new HttpHost(HTTPS.getId(), "api.frankfurter.app");

    private final @Nonnull HttpHost host;

    protected AbstractFrankfurterAPIRequest() {
        this(PUBLIC_INSTANCE_HOST);
    }

    protected AbstractFrankfurterAPIRequest(@Nonnull HttpHost customHost) {
        this.host = Objects.requireNonNull(customHost, "customHost is null");
    }

    @Override
    protected final @Nonnull HttpHost getHost() {
        return host;
    }

    @Override
    public boolean equals(Object obj) { // Non-final
        return obj instanceof AbstractFrankfurterAPIRequest that
                && Objects.equals(this.getAccessKey(), that.getAccessKey())
                && Objects.equals(this.host, that.host);
    }

    @Override
    public int hashCode() { // Non-final
        return Objects.hash(getAccessKey(), host);
    }
}
