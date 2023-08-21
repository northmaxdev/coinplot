// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateSetRequest;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateSetRequests;
import io.github.northmaxdev.coinplot.lang.MoreCollections;
import io.github.northmaxdev.coinplot.lang.chrono.LocalDateInterval;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class FixerExchangeRateSetRequest
        extends AbstractFixerAPIRequest
        implements ExchangeRateSetRequest {

    private final @Nullable Currency base;
    private final @Nonnull Set<Currency> targets;
    private final @Nonnull LocalDateInterval dateInterval;

    public FixerExchangeRateSetRequest(
            @Nonnull String accessKeyValue,
            @Nullable Currency base,
            @Nullable Set<Currency> targets,
            @Nonnull LocalDateInterval dateInterval) {
        super(accessKeyValue);
        this.base = base;
        this.targets = MoreCollections.emptyIfNull(targets);
        this.dateInterval = Objects.requireNonNull(dateInterval);
    }

    @Override
    public Optional<Currency> getBase() {
        return Optional.ofNullable(base);
    }

    @Override
    public @Nonnull Set<Currency> getTargets() {
        return targets;
    }

    @Override
    public @Nonnull LocalDateInterval getDateInterval() {
        return dateInterval;
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
                && ExchangeRateSetRequests.commonPropertiesAreEqual(this, that);
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ ExchangeRateSetRequests.hashCommonProperties(this);
    }
}
