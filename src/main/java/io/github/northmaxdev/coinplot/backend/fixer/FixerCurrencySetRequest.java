// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import io.github.northmaxdev.coinplot.backend.core.currency.CurrencySetRequest;
import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.NameValuePair;

import java.util.List;
import java.util.Objects;

public final class FixerCurrencySetRequest extends AbstractFixerAPIRequest implements CurrencySetRequest {

    public FixerCurrencySetRequest(@Nonnull String accessKey) {
        super(accessKey);
    }

    @Override
    protected @Nonnull String getEndpoint() {
        return "symbols";
    }

    @Override
    protected List<NameValuePair> getAdditionalParameters() {
        return List.of();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof FixerCurrencySetRequest that
                // Access key is the only state in this class
                && Objects.equals(this.getAccessKeyParameter(), that.getAccessKeyParameter());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getAccessKeyParameter());
    }
}
