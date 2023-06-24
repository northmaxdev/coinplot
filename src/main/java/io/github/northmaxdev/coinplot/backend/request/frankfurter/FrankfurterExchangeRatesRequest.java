// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.request.frankfurter;

import io.github.northmaxdev.coinplot.backend.currency.Currency;
import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.joining;

public final class FrankfurterExchangeRatesRequest extends AbstractFrankfurterRequest {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    private final @Nonnull Currency base;
    private final @Nonnull Collection<Currency> targets;
    private final @Nonnull LocalDate start;
    private final @Nonnull LocalDate end;

    public FrankfurterExchangeRatesRequest(
            @Nonnull HttpHost customHost,
            @Nonnull Currency base,
            @Nonnull Collection<Currency> targets,
            @Nonnull LocalDate start,
            @Nonnull LocalDate end) {
        super(customHost);
        this.base = base;
        this.targets = targets;
        this.start = start;
        this.end = end;
    }

    public FrankfurterExchangeRatesRequest(
            @Nonnull Currency base,
            @Nonnull Collection<Currency> targets,
            @Nonnull LocalDate start,
            @Nonnull LocalDate end) {
        super();
        this.base = base;
        this.targets = targets;
        this.start = start;
        this.end = end;
    }

    @Override
    protected @Nonnull List<String> getPathSegments() {
        // TODO: All this can be computed once and cached
        String endpoint = start.format(DATE_FORMAT) + ".." + end.format(DATE_FORMAT);
        return List.of(endpoint);
    }

    @Override
    protected @Nonnull List<NameValuePair> getParameters() {
        // TODO: All this can be computed once and cached

        List<NameValuePair> parameters = new ArrayList<>(2);

        NameValuePair baseParameter = new BasicNameValuePair("from", base.getThreeLetterISOCode());
        parameters.add(baseParameter);

        if (!targets.isEmpty()) {
            String joinedTargets = targets.stream()
                    .filter(Objects::nonNull)
                    .map(Currency::getThreeLetterISOCode)
                    .collect(joining(","));
            NameValuePair targetsParameter = new BasicNameValuePair("to", joinedTargets);
            parameters.add(targetsParameter);
        }

        return parameters;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof FrankfurterExchangeRatesRequest that
               && Objects.equals(this.getHost(), that.getHost())
               && Objects.equals(this.base, that.base)
               && Objects.equals(this.targets, that.targets)
               && Objects.equals(this.start, that.start)
               && Objects.equals(this.end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHost(), base, targets, start, end);
    }
}
