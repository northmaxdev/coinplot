// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateSetRequest;
import io.github.northmaxdev.coinplot.backend.core.exchange.impl.ExchangeRateSetRequests;
import io.github.northmaxdev.coinplot.lang.chrono.LocalDateInterval;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.hc.core5.http.HttpHost;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public final class FrankfurterExchangeRateSetRequest
        extends AbstractFrankfurterAPIRequest
        implements ExchangeRateSetRequest {

    private final @Nullable Currency base;
    private final Set<Currency> targets;
    private final @Nonnull LocalDateInterval dateInterval;

    public FrankfurterExchangeRateSetRequest(
            @Nullable Currency base,
            Set<Currency> targets,
            @Nonnull LocalDateInterval dateInterval) {
        this.base = base;
        this.targets = targets;
        this.dateInterval = dateInterval;
    }

    public FrankfurterExchangeRateSetRequest(
            @Nonnull HttpHost customHost,
            @Nullable Currency base,
            Set<Currency> targets,
            @Nonnull LocalDateInterval dateInterval) {
        super(customHost);
        this.base = base;
        this.targets = targets;
        this.dateInterval = dateInterval;
    }

    @Override
    public Optional<Currency> getBase() {
        return Optional.ofNullable(base);
    }

    @Override
    public Set<Currency> getTargets() {
        return targets;
    }

    @Override
    public @Nonnull LocalDateInterval getDateInterval() {
        return dateInterval;
    }

    @Override
    protected @Nonnull String getEndpoint() {
        return ISO_LOCAL_DATE.format(dateInterval.start()) + ".." + ISO_LOCAL_DATE.format(dateInterval.end());
    }

    @Override
    protected Map<String, String> getParameters() {
        return new ExchangeRateSetRequests.ParametersBuilder(this)
                .baseName("from")
                .targetsName("to")
                .build();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof FrankfurterExchangeRateSetRequest that
                && super.equals(obj) // For superclass fields
                && ExchangeRateSetRequests.basicPropertiesAreEqual(this, that);
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ ExchangeRateSetRequests.hashBasicProperties(this);
    }
}
