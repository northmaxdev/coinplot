// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.backend.core.web.request.AbstractAPIRequest;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.hc.core5.http.HttpHost;

import java.util.Objects;

import static org.apache.hc.core5.http.URIScheme.HTTPS;

abstract class AbstractFrankfurterAPIRequest extends AbstractAPIRequest { // Package-private

    private static final HttpHost PUBLIC_INSTANCE_HOST = new HttpHost(HTTPS.getId(), "api.frankfurter.app");

    private final @Nonnull HttpHost host;

    protected AbstractFrankfurterAPIRequest() {
        host = PUBLIC_INSTANCE_HOST;
    }

    protected AbstractFrankfurterAPIRequest(@Nullable HttpHost customHost) {
        host = Objects.requireNonNullElse(customHost, PUBLIC_INSTANCE_HOST);
    }

    @Override
    protected final @Nonnull HttpHost getHost() {
        return host;
    }
}
