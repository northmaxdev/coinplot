// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.request.currencyapi;

import io.github.northmaxdev.coinplot.backend.request.AbstractAPIRequest;
import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.HttpHost;

import java.util.List;

import static org.apache.hc.core5.http.URIScheme.HTTPS;

public sealed abstract class AbstractCurrencyAPIRequest extends AbstractAPIRequest
        permits CurrencyAPICurrenciesRequest, CurrencyAPIExchangeRatesRequest {

    private static final HttpHost HOST = new HttpHost(HTTPS.getId(), "api.currencyapi.com");

    @Override
    protected final @Nonnull HttpHost getHost() {
        return HOST;
    }

    @Override
    protected final @Nonnull List<String> getPathSegments() {
        // TODO: This can be precomputed once
        return List.of("v3", getEndpoint());
    }

    protected abstract @Nonnull String getEndpoint();
}
