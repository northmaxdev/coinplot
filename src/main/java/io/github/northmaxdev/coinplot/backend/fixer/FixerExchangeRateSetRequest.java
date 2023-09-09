// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeBatch;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateSetRequest;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateSetRequests;
import jakarta.annotation.Nonnull;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

public final class FixerExchangeRateSetRequest
        extends AbstractFixerAPIRequest
        implements ExchangeRateSetRequest {

    private final @Nonnull ExchangeBatch data;

    public FixerExchangeRateSetRequest(@Nonnull String accessKeyValue, @Nonnull ExchangeBatch data) {
        super(accessKeyValue);
        this.data = Objects.requireNonNull(data);
    }

    @Override
    public @Nonnull ExchangeBatch getRequestData() {
        return data;
    }

    @Override
    protected @Nonnull String getEndpoint() {
        return "timeseries";
    }

    @Override
    protected @Nonnull Map<String, String> getParameters() {
        return new ExchangeRateSetRequests.ParametersBuilder(this)
                .baseParameterName("base")
                .targetsParameterName("symbols")
                .startParameterName("start_date")
                .endParameterName("end_date")
                .dateFormatter(DateTimeFormatter.ISO_LOCAL_DATE)
                .build();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof FixerExchangeRateSetRequest that
                && super.equals(obj) // For superclass fields
                && Objects.equals(this.data, that.data);
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }
}
