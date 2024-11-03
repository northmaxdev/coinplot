// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import io.github.northmaxdev.coinplot.backend.core.exchange.Exchange;
import io.github.northmaxdev.coinplot.lang.chrono.LocalDateInterval;

import java.util.Currency;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableSet;

// A "compressed" way to represent a bunch of exchanges.
// The following Exchange instances:
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
// An ExchangeBatch can be very easily converted to a sequence of Exchange instances, but not the other way around.
// There will always be at least one non-null target currency and one non-null date. Thus, methods like stream() and
// toSet() never return empty sequences.
public record ExchangeBatch(Currency base, Set<Currency> targets, LocalDateInterval dateInterval) {

    public ExchangeBatch(Currency base, Set<Currency> targets, LocalDateInterval dateInterval) {
        this.base = Objects.requireNonNull(base);
        this.dateInterval = Objects.requireNonNull(dateInterval);

        if (targets.isEmpty()) { // Implicit null-check on the set itself
            throw new IllegalArgumentException("An ExchangeBatch must contain at least one target");
        }
        this.targets = Set.copyOf(targets); // Implicit null-checks on the set's items
    }

    public Stream<Exchange> stream() {
        return dateInterval.stream().mapMulti((date, buffer) -> {
            for (Currency target : targets) {
                Exchange exchange = new Exchange(base, target, date);
                buffer.accept(exchange);
            }
        });
    }

    public Set<Exchange> toSet() {
        return stream().collect(toUnmodifiableSet());
    }

    public int size() {
        return targets.size() * dateInterval.length();
    }
}
