// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeBatch;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateSetRequest;
import io.github.northmaxdev.coinplot.lang.chrono.LocalDateInterval;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.hc.core5.http.HttpHost;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

final class FrankfurterExchangeRateSetRequest // Package-private
        extends AbstractFrankfurterAPIRequest
        implements ExchangeRateSetRequest {

    private static final DateTimeFormatter ENDPOINT_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private final @Nonnull ExchangeBatch data;

    public FrankfurterExchangeRateSetRequest(@Nonnull ExchangeBatch data) {
        this.data = Objects.requireNonNull(data);
    }

    public FrankfurterExchangeRateSetRequest(@Nullable HttpHost customHost, @Nonnull ExchangeBatch data) {
        super(customHost);
        this.data = Objects.requireNonNull(data);
    }

    @Override
    public @Nonnull ExchangeBatch getRequestedExchanges() {
        return data;
    }

    @Override
    protected @Nonnull String getEndpoint() {
        LocalDateInterval dateInterval = data.dateInterval();
        return ENDPOINT_DATE_FORMATTER.format(dateInterval.start()) + ".." + ENDPOINT_DATE_FORMATTER.format(dateInterval.end());
    }

    @Override
    protected @Nonnull Map<String, String> getParameters() {
        return new ParametersBuilder(this)
                .baseParameterName("from")
                .targetsParameterName("to")
                .build();
    }
}
