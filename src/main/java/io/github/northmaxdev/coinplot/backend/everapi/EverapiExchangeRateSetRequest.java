// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateSetRequest;
import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.NameValuePair;

import java.util.List;

public final class EverapiExchangeRateSetRequest extends AbstractEverapiAPIRequest implements ExchangeRateSetRequest {

    // TODO: Properties

    public EverapiExchangeRateSetRequest() {
        super();
    }

    @Override
    protected @Nonnull String getEndpoint() {
        return "range";
    }

    @Override
    protected List<NameValuePair> getAdditionalParameters() {
        // TODO. See https://currencyapi.com/docs/range#range-historical-exchange-rates
        throw new UnsupportedOperationException();
    }

    // TODO: equals/hashCode
}
