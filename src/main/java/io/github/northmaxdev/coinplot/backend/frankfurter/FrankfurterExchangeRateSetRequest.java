// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateSetRequest;
import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.NameValuePair;

import java.util.List;

public final class FrankfurterExchangeRateSetRequest
        extends AbstractFrankfurterAPIRequest
        implements ExchangeRateSetRequest {

    // TODO: Properties

    public FrankfurterExchangeRateSetRequest() {
        super();
    }

    public FrankfurterExchangeRateSetRequest(@Nonnull HttpHost customHost) {
        super(customHost);
    }

    @Override
    protected @Nonnull String getEndpoint() {
        // TODO. See https://www.frankfurter.app/docs/#timeseries
        throw new UnsupportedOperationException();
    }

    @Override
    protected List<NameValuePair> getAdditionalParameters() {
        // TODO. See https://www.frankfurter.app/docs/#timeseries
        throw new UnsupportedOperationException();
    }

    // TODO: equals/hashCode
}
