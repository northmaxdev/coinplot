// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import io.github.northmaxdev.coinplot.backend.core.web.request.APIKey;
import io.github.northmaxdev.coinplot.backend.core.web.request.AbstractAPIRequest;
import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.HttpHost;

import java.util.Objects;
import java.util.Optional;

import static org.apache.hc.core5.http.URIScheme.HTTPS;

public abstract class AbstractFixerAPIRequest extends AbstractAPIRequest {

    private static final HttpHost HOST = new HttpHost(HTTPS.getId(), "data.fixer.io");

    protected AbstractFixerAPIRequest(@Nonnull String accessKey) {
        super(APIKey.asQueryParameter("access_key", accessKey));
    }

    @Override
    protected final @Nonnull HttpHost getHost() {
        return HOST;
    }

    @Override
    protected final Optional<String> getRootPathSegment() {
        return Optional.of("api");
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AbstractFixerAPIRequest that
                && Objects.equals(this.getAccessKey(), that.getAccessKey());
    }
}
