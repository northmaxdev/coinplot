// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateSetRequest;
import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.NameValuePair;

import java.util.List;

public final class FixerExchangeRateSetRequest extends AbstractFixerAPIRequest implements ExchangeRateSetRequest {

    // TODO: Properties

    public FixerExchangeRateSetRequest(@Nonnull String accessKey) {
        super(accessKey);
    }

    @Override
    protected @Nonnull String getEndpoint() {
        return "timeseries";
    }

    @Override
    protected List<NameValuePair> getAdditionalParameters() {
        // TODO. See https://fixer.io/documentation#timeseries
        throw new UnsupportedOperationException();
    }

    // TODO: equals/hashCode
}
