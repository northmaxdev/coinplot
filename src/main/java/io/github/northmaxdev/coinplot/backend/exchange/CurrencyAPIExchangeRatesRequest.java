// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.exchange;

import com.google.common.collect.ImmutableCollection;
import io.github.northmaxdev.coinplot.backend.currency.Currency;
import io.github.northmaxdev.coinplot.backend.request.AbstractCurrencyAPIRequest;
import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

public final class CurrencyAPIExchangeRatesRequest extends AbstractCurrencyAPIRequest {

    private static final List<String> PATH_SEGMENTS = createPathSegments("range");

    private final @Nonnull Instant start;
    private final @Nonnull Instant end;
    private final @Nonnull Currency base;
    private final @Nonnull ImmutableCollection<Currency> targets;
    // TODO: Implement "accuracy" parameter

    public CurrencyAPIExchangeRatesRequest(
            @Nonnull Instant start,
            @Nonnull Instant end,
            @Nonnull Currency base,
            @Nonnull ImmutableCollection<Currency> targets) {
        this.start = start;
        this.end = end;
        this.base = base;
        this.targets = targets;
    }

    public @Nonnull Instant getStart() {
        return start;
    }

    public @Nonnull Instant getEnd() {
        return end;
    }

    public @Nonnull Currency getBase() {
        return base;
    }

    public @Nonnull ImmutableCollection<Currency> getTargets() {
        return targets;
    }

    @Override
    protected @Nonnull List<String> getPathSegments() {
        return PATH_SEGMENTS;
    }

    @Override
    protected @Nonnull List<NameValuePair> getParameters() {
        // TODO: Cache this
        List<NameValuePair> parameters = new ArrayList<>(4);

        NameValuePair startParameter = new BasicNameValuePair("datetime_start", ISO_INSTANT.format(start));
        parameters.add(startParameter);

        NameValuePair endParameter = new BasicNameValuePair("datetime_end", ISO_INSTANT.format(end));
        parameters.add(endParameter);

        NameValuePair baseParameter = new BasicNameValuePair("base_currency", base.getCode());
        parameters.add(baseParameter);

        Optional<NameValuePair> targetsParameter = joinCurrenciesToParameter("currencies", targets);
        targetsParameter.ifPresent(parameters::add);

        return parameters;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CurrencyAPIExchangeRatesRequest that
                && Objects.equals(this.start, that.start)
                && Objects.equals(this.end, that.end)
                && Objects.equals(this.base, that.base)
                && Objects.equals(this.targets, that.targets);
    }

    @Override
    public int hashCode() {
        // TODO: Cache this
        return Objects.hash(start, end, base, targets);
    }
}
