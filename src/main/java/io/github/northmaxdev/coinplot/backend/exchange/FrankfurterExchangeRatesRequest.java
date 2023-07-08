// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.exchange;

import com.google.common.collect.ImmutableCollection;
import io.github.northmaxdev.coinplot.backend.currency.Currency;
import io.github.northmaxdev.coinplot.backend.web.request.AbstractFrankfurterRequest;
import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public final class FrankfurterExchangeRatesRequest extends AbstractFrankfurterRequest implements ExchangeRatesRequest {

    private final @Nonnull LocalDate start;
    private final @Nonnull LocalDate end;
    private final @Nonnull Currency base;
    private final @Nonnull ImmutableCollection<Currency> targets;

    public FrankfurterExchangeRatesRequest(
            @Nonnull HttpHost customHost,
            @Nonnull LocalDate start,
            @Nonnull LocalDate end,
            @Nonnull Currency base,
            @Nonnull ImmutableCollection<Currency> targets) {
        super(customHost);
        this.start = start;
        this.end = end;
        this.base = base;
        this.targets = targets;
    }

    public FrankfurterExchangeRatesRequest(
            @Nonnull LocalDate start,
            @Nonnull LocalDate end,
            @Nonnull Currency base,
            @Nonnull ImmutableCollection<Currency> targets) {
        super();
        this.start = start;
        this.end = end;
        this.base = base;
        this.targets = targets;
    }

    public @Nonnull LocalDate getStart() {
        return start;
    }

    public @Nonnull LocalDate getEnd() {
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
        // TODO: Cache this
        String endpoint = start.format(ISO_LOCAL_DATE) + ".." + end.format(ISO_LOCAL_DATE);
        return List.of(endpoint);
    }

    @Override
    protected @Nonnull List<NameValuePair> getParameters() {
        // TODO Cache this
        NameValuePair baseParameter = new BasicNameValuePair("from", base.getCode());
        Optional<NameValuePair> targetsParameter = joinCurrenciesToParameter("to", targets);

        return targetsParameter.map(tp -> List.of(baseParameter, tp))
                .orElse(List.of(baseParameter));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof FrankfurterExchangeRatesRequest that
                && Objects.equals(this.getHost(), that.getHost())
                && Objects.equals(this.start, that.start)
                && Objects.equals(this.end, that.end)
                && Objects.equals(this.base, that.base)
                && Objects.equals(this.targets, that.targets);
    }

    @Override
    public int hashCode() {
        // TODO: Cache this
        return Objects.hash(getHost(), start, end, base, targets);
    }
}
