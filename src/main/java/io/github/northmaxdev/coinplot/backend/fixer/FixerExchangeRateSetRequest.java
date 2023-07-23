// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateSetRequest;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateSetRequestSupport;
import io.github.northmaxdev.coinplot.lang.LocalDateInterval;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public final class FixerExchangeRateSetRequest
        extends AbstractFixerAPIRequest
        implements ExchangeRateSetRequest {

    private final @Nullable Currency base;
    private final Set<Currency> targets;
    private final @Nonnull LocalDateInterval dateInterval;

    public FixerExchangeRateSetRequest(
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
        return "timeseries";
    }

    @Override
    protected Map<String, String> getParameters() {
        Map<String, String> parameters = new HashMap<>(4);
        parameters.put("start_date", ISO_LOCAL_DATE.format(dateInterval.start()));
        parameters.put("end_date", ISO_LOCAL_DATE.format(dateInterval.end()));

        if (base != null) {
            parameters.put("base", base.getCode());
        }

        ExchangeRateSetRequestSupport.joinTargetCodes(this)
                .ifPresent(s -> parameters.put("symbols", s));

        return parameters;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof FixerExchangeRateSetRequest that
                && super.equals(obj) // For superclass fields
                && ExchangeRateSetRequestSupport.areBasicPropertiesEqual(this, that);
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ ExchangeRateSetRequestSupport.hashBasicProperties(this);
    }
}
