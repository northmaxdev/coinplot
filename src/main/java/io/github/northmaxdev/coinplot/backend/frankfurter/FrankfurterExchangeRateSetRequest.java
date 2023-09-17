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

public final class FrankfurterExchangeRateSetRequest
        extends AbstractFrankfurterAPIRequest
        implements ExchangeRateSetRequest {

    private static final DateTimeFormatter ENDPOINT_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private final @Nonnull ExchangeBatch data;

    public FrankfurterExchangeRateSetRequest(@Nonnull ExchangeBatch data) {
        this(null, data);
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

    @Override
    public boolean equals(Object obj) {
        return obj instanceof FrankfurterExchangeRateSetRequest that
                && super.equals(obj) // For superclass fields
                && Objects.equals(this.data, that.data);
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }
}
