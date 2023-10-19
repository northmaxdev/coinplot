// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import io.github.northmaxdev.coinplot.backend.core.web.request.AbstractAPIRequest;
import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.HttpHost;

import java.util.Optional;

import static org.apache.hc.core5.http.URIScheme.HTTPS;

abstract class AbstractFixerAPIRequest extends AbstractAPIRequest { // Package-private

    private static final HttpHost HOST = new HttpHost(HTTPS.getId(), "data.fixer.io");

    protected AbstractFixerAPIRequest(@Nonnull String accessKeyValue) {
        super(AccessKey.asQueryParameter("access_key", accessKeyValue));
    }

    @Override
    protected final @Nonnull HttpHost getHost() {
        return HOST;
    }

    @Override
    protected final Optional<String> getRootPathSegment() {
        return Optional.of("api");
    }
}
