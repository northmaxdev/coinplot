// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateSetRequest;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateSetRequests;
import io.github.northmaxdev.coinplot.lang.chrono.LocalDateInterval;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class EverapiExchangeRateSetRequest
        extends AbstractEverapiAPIRequest
        implements ExchangeRateSetRequest {

    private static final DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .appendLiteral("T00:00:00Z")
            .toFormatter();

    private final @Nullable Currency base;
    private final Set<Currency> targets;
    private final @Nonnull LocalDateInterval dateInterval;

    public EverapiExchangeRateSetRequest(
            @Nonnull String accessKey,
            @Nullable Currency base,
            Set<Currency> targets,
            @Nonnull LocalDateInterval dateInterval) {
        super(accessKey);
        this.base = base;
        this.targets = targets;
        this.dateInterval = Objects.requireNonNull(dateInterval, "dateInterval is null");
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
        return "range";
    }

    @Override
    protected Map<String, String> getParameters() {
        return new ExchangeRateSetRequests.ParametersBuilder(this)
                .baseName("base_currency")
                .targetsName("currencies")
                .startName("datetime_start")
                .endName("datetime_end")
                .dateFormatter(DATE_FORMATTER)
                .build();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EverapiExchangeRateSetRequest that
                && super.equals(obj) // For superclass fields
                && ExchangeRateSetRequests.basicPropertiesAreEqual(this, that);
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ ExchangeRateSetRequests.hashBasicProperties(this);
    }
}
