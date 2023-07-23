// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateSetRequest;
import io.github.northmaxdev.coinplot.backend.core.exchange.impl.ExchangeRateSetRequests;
import io.github.northmaxdev.coinplot.lang.LocalDateInterval;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class EverapiExchangeRateSetRequest
        extends AbstractEverapiAPIRequest
        implements ExchangeRateSetRequest {

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
        return "range";
    }

    @Override
    protected Map<String, String> getParameters() {
        return new ExchangeRateSetRequests.ParametersBuilder(this)
                .baseName("base_currency")
                .targetsName("currencies")
                .startName("datetime_start")
                .endName("datetime_end")
                // FIXME: Choose the correct formatter (https://currencyapi.com/docs/range)
                .dateFormatter(DateTimeFormatter.ISO_LOCAL_DATE)
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
