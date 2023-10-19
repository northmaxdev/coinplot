// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.lang.math.Percentage;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public final class ExchangeRateStatistics {

    private final @Nonnull DatelessExchange exchange;
    private final @Nonnull NavigableMap<LocalDate, BigDecimal> rateValueChronology;

    private ExchangeRateStatistics(@Nonnull DatelessExchange exchange, @Nonnull NavigableMap<LocalDate, BigDecimal> rateValueChronology) {
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
                .map(entry -> new ExchangeRateStatistics(entry.getKey(), entry.getValue()));
    }

    public @Nonnull DatelessExchange getExchange() {
        return exchange;
    }

    public @Nonnull NavigableMap<LocalDate, BigDecimal> getRateValueChronology() {
        return Collections.unmodifiableNavigableMap(rateValueChronology);
    }

    public Optional<BigDecimal> getLowestValue() {
        return rateValueChronology.values()
                .stream()
                .min(BigDecimal::compareTo);
    }

    public Optional<BigDecimal> getHighestValue() {
        return rateValueChronology.values()
                .stream()
                .max(BigDecimal::compareTo);
    }

    public Optional<BigDecimal> getLatestAvailableValue() {
        @Nullable Map.Entry<LocalDate, BigDecimal> latestEntry = rateValueChronology.lastEntry();
        return Optional.ofNullable(latestEntry)
                .map(Map.Entry::getValue);
    }

    // Change % of the last two values in the chronology.
    // If there are less than two values available,
    // or the before-last value is zero (and thus it's impossible to calculate the percentage),
    // an empty Optional is returned.
    public Optional<Percentage> getLatestChangePercentage() {
        if (rateValueChronology.size() < 2) {
            return Optional.empty();
        }

        // Even if the Map implementation of rateValueChronology is mutable, we do not expose it as such,
        // so these should not return nulls considering the size-check above.
        @Nonnull Map.Entry<LocalDate, BigDecimal> lastEntry = rateValueChronology.lastEntry();
        @Nonnull Map.Entry<LocalDate, BigDecimal> beforeLastEntry = rateValueChronology.lowerEntry(lastEntry.getKey());

        BigDecimal lastValue = lastEntry.getValue();
        BigDecimal beforeLastValue = beforeLastEntry.getValue();

        if (lastValue.equals(BigDecimal.ZERO)) {
            return Optional.empty();
        }

        Percentage p = Percentage.ofChange(beforeLastValue, lastValue);
        return Optional.of(p);
    }

    // Change % of the lowest value to the highest.
    // If neither the lowest nor the highest values are available,
    // or the lowest value is zero (and thus it's impossible to calculate the percentage),
    // an empty Optional is returned.
    // The method's name is based on the following definition:
    // https://www.statista.com/statistics-glossary/definition/204/extreme_value/
    public Optional<Percentage> getExtremesChangePercentage() {
        Optional<BigDecimal> min = getLowestValue();
        Optional<BigDecimal> max = getHighestValue();

        if (min.isEmpty() || max.isEmpty() || BigDecimal.ZERO.equals(min.get())) {
            return Optional.empty();
        }

        Percentage p = Percentage.ofChange(min.get(), max.get());
        return Optional.of(p);
    }

    @Override
    public @Nonnull String toString() {
        return "ExchangeRateStatistics[" + exchange + ']';
    }
}
