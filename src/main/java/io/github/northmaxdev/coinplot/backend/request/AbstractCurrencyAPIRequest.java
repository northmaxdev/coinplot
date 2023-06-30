// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.request;

import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.HttpHost;

import java.util.List;

import static org.apache.hc.core5.http.URIScheme.HTTPS;

public abstract class AbstractCurrencyAPIRequest extends AbstractAPIRequest {

    private static final HttpHost HOST = new HttpHost(HTTPS.getId(), "api.currencyapi.com");

    protected AbstractCurrencyAPIRequest() {
        super();
    }

    @Override
    protected final @Nonnull HttpHost getHost() {
        return HOST;
    }

    // Since the mappings of the only endpoints we require follow the same non-changing structure, we can simply create
    // a static class constant for every subclass's getPathSegments(). This method is intended just for that.
    protected static List<String> createPathSegments(@Nonnull String endpoint) {
        return List.of("v3", endpoint);
    }
}
