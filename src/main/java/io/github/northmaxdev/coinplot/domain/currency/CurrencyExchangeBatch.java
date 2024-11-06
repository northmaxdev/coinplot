// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain.currency;

import io.github.northmaxdev.coinplot.util.LocalDateInterval;

import java.util.Currency;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

// A "compressed" way to represent a bunch of currency exchanges.
// The following CurrencyExchange instances:
//
//     {FOO, BAR, 2000-01-01}
//     {FOO, BAZ, 2000-01-01}
//     {FOO, BAR, 2000-01-02}
//     {FOO, BAZ, 2000-01-02}
//
// ...can be represented as the following CurrencyExchangeBatch:
//
//     {base=FOO, targets={BAR,BAZ}, dateInterval=[2000-01-01, 2000-01-03)}
//
// A CurrencyExchangeBatch easily converts into a sequence of CurrencyExchange instances, but not the other way around.
// There will always be at least one target currency and one date (both non-null), so stream() is never empty.
public record CurrencyExchangeBatch(Currency base, Set<Currency> targets, LocalDateInterval dateInterval) {

    public CurrencyExchangeBatch(Currency base, Set<Currency> targets, LocalDateInterval dateInterval) {
        this.base = Objects.requireNonNull(base);
        this.dateInterval = Objects.requireNonNull(dateInterval);

        if (targets.isEmpty()) { // Implicit null-check on the set itself
            throw new IllegalArgumentException("must provide at least one target");
        }
        this.targets = Set.copyOf(targets); // Implicit null-checks on the set's items
    }

    public Stream<CurrencyExchange> stream() {
        return dateInterval.stream().mapMulti((date, buffer) -> {
            for (Currency target : targets) {
                CurrencyExchange exchange = new CurrencyExchange(base, target, date);
                buffer.accept(exchange);
            }
        });
    }

    public int size() {
        // The multiplication can theoretically yield an int64 result.
        // If this is realistic, this method should return an int64.
        // If this is not realistic, this method should return an int32 with an overflow check (chosen option).
        return Math.multiplyExact(targets.size(), dateInterval.length());
    }
}
