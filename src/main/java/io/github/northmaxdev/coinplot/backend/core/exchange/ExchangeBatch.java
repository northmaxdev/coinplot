// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.lang.MoreCollections;
import io.github.northmaxdev.coinplot.lang.chrono.LocalDateInterval;
import jakarta.annotation.Nonnull;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableSet;

// A "compressed" way to represent a bunch of exchanges. The following Exchange instances:
//
//     {FOO, BAR, 2000-01-01}
//     {FOO, BAZ, 2000-01-01}
//     {FOO, BAR, 2000-01-02}
//     {FOO, BAZ, 2000-01-02}
//
// ...can be represented as the following ExchangeBatch:
//
//     {base=FOO, targets={BAR,BAZ}, dateInterval=[2000-01-01, 2000-01-03)}
//
// An ExchangeBatch can be very easily converted to a collection of Exchange instances, but not the other way around.
// This is a general-purpose concept that is used by different parts of the codebase, such as the DB or web layers.
public record ExchangeBatch(@Nonnull Currency base, @Nonnull Set<Currency> targets, @Nonnull LocalDateInterval dateInterval) {

    public ExchangeBatch(@Nonnull Currency base, @Nonnull Set<Currency> targets, @Nonnull LocalDateInterval dateInterval) {
        this.base = Objects.requireNonNull(base);
        this.targets = MoreCollections.requireNonEmptyWithoutNulls(targets);
        this.dateInterval = Objects.requireNonNull(dateInterval);
    }

    public @Nonnull Stream<Exchange> stream() {
        return dateInterval.dates()
                .mapMulti((date, buffer) -> {
                    for (Currency target : targets) {
                        Exchange exchange = new Exchange(base, target, date);
                        buffer.accept(exchange);
                    }
                });
    }

    public @Nonnull Set<Exchange> toSet() {
        return stream().collect(toUnmodifiableSet());
    }
}
