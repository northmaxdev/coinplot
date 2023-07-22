// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateSetRequest;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateSetRequestSupport;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.hc.core5.http.HttpHost;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public final class FrankfurterExchangeRateSetRequest
        extends AbstractFrankfurterAPIRequest
        implements ExchangeRateSetRequest<LocalDate> {

    private final @Nullable Currency base;
    private final Set<Currency> targets;
    private final @Nonnull LocalDate start;
    private final @Nullable LocalDate end;

    public FrankfurterExchangeRateSetRequest(
            @Nullable Currency base,
            Set<Currency> targets,
            @Nonnull LocalDate start,
            @Nullable LocalDate end) {
        super();
        this.base = base;
        this.targets = targets;
        this.start = start;
        this.end = end;
    }

    public FrankfurterExchangeRateSetRequest(
            @Nonnull HttpHost customHost,
            @Nullable Currency base,
            Set<Currency> targets,
            @Nonnull LocalDate start,
            @Nullable LocalDate end) {
        super(customHost);
        this.base = base;
        this.targets = targets;
        this.start = start;
        this.end = end;
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
    public Optional<LocalDate> getStart() {
        return Optional.of(start);
    }

    @Override
    public Optional<LocalDate> getEnd() {
        return Optional.ofNullable(end);
    }

    @Override
    protected @Nonnull String getEndpoint() {
        StringBuilder sb = new StringBuilder(22) // Maximum possible length of the resulting string
                .append(start.format(ISO_LOCAL_DATE))
                .append("..");

        if (end != null) {
            sb.append(end.format(ISO_LOCAL_DATE));
        }

        return sb.toString();
    }

    @Override
    protected Map<String, String> getParameters() {
        Map<String, String> parameters = new HashMap<>(3);
        parameters.put("amount", Integer.toString(1)); // Just to be explicit against the web API

        if (base != null) {
            parameters.put("from", base.getCode());
        }

        ExchangeRateSetRequestSupport.joinTargetCodes(this)
                .ifPresent(s -> parameters.put("to", s));

        return parameters;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof FrankfurterExchangeRateSetRequest that
                && super.equals(obj) // For superclass fields
                && ExchangeRateSetRequestSupport.areBasicPropertiesEqual(this, that);
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ ExchangeRateSetRequestSupport.hashBasicProperties(this);
    }
}
