// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeBatch;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateSetRequest;
import jakarta.annotation.Nonnull;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

final class FixerExchangeRateSetRequest // Package-private
        extends AbstractFixerAPIRequest
        implements ExchangeRateSetRequest {

    private final @Nonnull ExchangeBatch data;

    public FixerExchangeRateSetRequest(@Nonnull String accessKey, @Nonnull ExchangeBatch data) {
        super(accessKey);
        this.data = Objects.requireNonNull(data);
    }

    @Override
    public @Nonnull ExchangeBatch getRequestedExchanges() {
        return data;
    }

    @Override
    protected @Nonnull String getEndpoint() {
        return "timeseries";
    }

    @Override
    protected @Nonnull Map<String, String> getParameters() {
        return new ParametersBuilder(this)
                .baseParameterName("base")
                .targetsParameterName("symbols")
                .startParameterName("start_date")
                .endParameterName("end_date")
                .dateFormatter(DateTimeFormatter.ISO_LOCAL_DATE)
                .build();
    }
}
