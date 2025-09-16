// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import io.github.northmaxdev.coinplot.langext.LocalDateInterval;

import java.util.Currency;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * A "compressed" way to represent a bunch of {@link DatedExchange} instances.
 * <p>
 * The following {@link DatedExchange} instances:
 * <pre>{@code
 * {FOO, BAR, 2000-01-01}
 * {FOO, BAZ, 2000-01-01}
 * {FOO, BAR, 2000-01-02}
 * {FOO, BAZ, 2000-01-02}}
 * </pre>
 * <p>
 * ...can be represented as the following {@link DatedExchangeZip}:
 * <pre>{@code
 * {base=FOO, targets={BAR,BAZ}, dateInterval=[2000-01-01, 2000-01-03)}}
 * </pre>
 * <p>
 * A {@link DatedExchangeZip} can easily "expand" into a sequence of {@link DatedExchange} instances, but not the other way around.
 * <p>
 * <i><b>The word "zip" here is used as a noun - same idea as ZIP files, but nothing to do with actual ZIP files.</b></i>
 *
 * @param base         base currency, not {@code null}
 * @param targets      target currencies, <b>at least one</b> and no {@code null}s (neither the set nor its contents)
 * @param dateInterval a date interval, not {@code null}
 */
public record DatedExchangeZip(Currency base, Set<Currency> targets, LocalDateInterval dateInterval) {

    public DatedExchangeZip(Currency base, Set<Currency> targets, LocalDateInterval dateInterval) {
        this.base = requireNonNull(base);
        this.dateInterval = requireNonNull(dateInterval);

        if (targets.isEmpty()) { // Implicit null-check on the set itself
            throw new IllegalArgumentException("must provide at least one currency target");
        }
        this.targets = Set.copyOf(targets); // Implicit null-checks on the set's items
    }

    public Stream<DatedExchange> stream() {
        return dateInterval.stream()
                .mapMulti((date, buffer) -> {
                    for (Currency target : targets) {
                        DatedExchange exchange = new DatedExchange(base, target, date);
                        buffer.accept(exchange);
                    }
                });
    }

    public int size() {
        return Math.multiplyExact(targets.size(), dateInterval.countDays());
    }
}
