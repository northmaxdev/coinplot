// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import io.github.northmaxdev.coinplot.backend.core.currency.CurrencySetRequest;
import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.NameValuePair;

import java.util.List;

public final class EverapiCurrencySetRequest extends AbstractEverapiAPIRequest implements CurrencySetRequest {

    public EverapiCurrencySetRequest() {
        super();
    }

    @Override
    protected @Nonnull String getEndpoint() {
        return "currencies";
    }

    @Override
    protected List<NameValuePair> getAdditionalParameters() {
        return List.of();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EverapiCurrencySetRequest;
    }

    @Override
    public int hashCode() {
        return EverapiCurrencySetRequest.class.hashCode();
    }
}
