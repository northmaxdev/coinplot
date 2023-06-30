// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.web.request;

import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.HttpHost;

import static org.apache.hc.core5.http.URIScheme.HTTPS;

public abstract class AbstractFrankfurterRequest extends AbstractAPIRequest {

    private static final HttpHost PUBLIC_INSTANCE_HOST = new HttpHost(HTTPS.getId(), "api.frankfurter.app");

    private final HttpHost host;

    protected AbstractFrankfurterRequest(@Nonnull HttpHost customHost) {
        this.host = customHost;
    }

    protected AbstractFrankfurterRequest() {
        this(PUBLIC_INSTANCE_HOST);
    }

    @Override
    protected final @Nonnull HttpHost getHost() {
        return host;
    }
}
