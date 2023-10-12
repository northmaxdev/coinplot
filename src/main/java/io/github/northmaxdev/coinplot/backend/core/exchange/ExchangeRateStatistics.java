// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableSortedMap;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public final class ExchangeRateStatistics {

    private final @Nonnull DatelessExchange exchange;
    private final @Nonnull SortedMap<LocalDate, BigDecimal> rateValueChronology;

    private ExchangeRateStatistics(@Nonnull DatelessExchange exchange, @Nonnull SortedMap<LocalDate, BigDecimal> rateValueChronology) {
        this.exchange = exchange;
        this.rateValueChronology = rateValueChronology;
    }

    ////////////////
    // Public API //
    ////////////////

    public static @Nonnull Stream<ExchangeRateStatistics> streamOf(@Nonnull Set<ExchangeRate> dataset) {
        // TODO (Performance): Consider stream parallelization.
        //  Stream::collect JavaDoc even mentions that it is safe to use with mutable data structures (in this case - TreeMap).
        return dataset.stream() // Implicit null-check
                // We should never have duplicate dates for a given exchange, so just pass a dummy merge function
                .collect(groupingBy(DatelessExchange::of, toMap(ExchangeRate::getDate, ExchangeRate::getValue, (a, b) -> a, TreeMap::new)))
                .entrySet()
                .stream()
                .map(entry -> {
                    SortedMap<LocalDate, BigDecimal> unmodifiableChronologyView = unmodifiableSortedMap(entry.getValue());
                    return new ExchangeRateStatistics(entry.getKey(), unmodifiableChronologyView);
                });
    }

    public @Nonnull DatelessExchange getExchange() {
        return exchange;
    }

    public @Nonnull SortedMap<LocalDate, BigDecimal> getRateValueChronology() {
        return rateValueChronology;
    }

    public Optional<BigDecimal> getMin() {
        return Optional.empty(); // FIXME (Implementation)
    }

    public Optional<BigDecimal> getMax() {
        return Optional.empty(); // FIXME (Implementation)
    }

    public @Nonnull BigDecimal getAverage() {
        return BigDecimal.ZERO; // FIXME (Implementation)
    }

    public Optional<BigDecimal> getEarliest() {
        return Optional.empty(); // FIXME (Implementation)
    }

    public Optional<BigDecimal> getLatest() {
        return Optional.empty(); // FIXME (Implementation)
    }

    public int getCount() {
        return rateValueChronology.size();
    }
}
