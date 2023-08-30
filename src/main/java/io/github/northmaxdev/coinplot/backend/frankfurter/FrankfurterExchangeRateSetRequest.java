// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateSetRequest;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateSetRequests;
import io.github.northmaxdev.coinplot.lang.MoreCollections;
import io.github.northmaxdev.coinplot.lang.chrono.LocalDateInterval;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.hc.core5.http.HttpHost;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class FrankfurterExchangeRateSetRequest
        extends AbstractFrankfurterAPIRequest
        implements ExchangeRateSetRequest {

    private static final DateTimeFormatter ENDPOINT_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private final @Nonnull Currency base;
    private final @Nonnull Set<Currency> targets;
    private final @Nonnull LocalDateInterval dateInterval;

    public FrankfurterExchangeRateSetRequest(
            @Nonnull Currency base,
            @Nonnull Set<Currency> targets,
            @Nonnull LocalDateInterval dateInterval) {
        this(null, base, targets, dateInterval);
    }

    public FrankfurterExchangeRateSetRequest(
            @Nullable HttpHost customHost,
            @Nonnull Currency base,
            @Nonnull Set<Currency> targets,
            @Nonnull LocalDateInterval dateInterval) {
        super(customHost);
        this.base = Objects.requireNonNull(base);
        this.targets = MoreCollections.requireNonEmptyWithoutNulls(targets);
        this.dateInterval = Objects.requireNonNull(dateInterval);
    }

    @Override
    public @Nonnull Currency getBase() {
        return base;
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
        return ENDPOINT_DATE_FORMATTER.format(dateInterval.start()) + ".." + ENDPOINT_DATE_FORMATTER.format(dateInterval.end());
    }

    @Override
    protected @Nonnull Map<String, String> getParameters() {
        return new ExchangeRateSetRequests.ParametersBuilder(this)
                .baseParameterName("from")
                .targetsParameterName("to")
                .build();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof FrankfurterExchangeRateSetRequest that
                && super.equals(obj) // For superclass fields
                && ExchangeRateSetRequests.commonPropertiesAreEqual(this, that);
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ ExchangeRateSetRequests.hashCommonProperties(this);
    }
}
