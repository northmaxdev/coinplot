// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.request.frankfurter;

import io.github.northmaxdev.coinplot.backend.request.AbstractAPIRequest;
import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.HttpHost;

import static org.apache.hc.core5.http.URIScheme.HTTPS;

public sealed abstract class AbstractFrankfurterRequest extends AbstractAPIRequest
        permits FrankfurterCurrenciesRequest, FrankfurterExchangeRatesRequest {

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

    // Due to complexities with equals/hashCode contract implementations and unit testing within big inheritance
    // hierarchies, only the final subclasses actually implement those methods. AbstractFrankfurterRequest explicitly
    // marks these methods as abstract to force their implementation by the final subclasses.

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();
}
