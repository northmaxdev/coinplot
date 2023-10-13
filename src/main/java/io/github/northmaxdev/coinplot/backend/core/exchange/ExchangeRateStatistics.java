// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.lang.Maps;
import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Stream;

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
                .map(entry -> new ExchangeRateStatistics(entry.getKey(), entry.getValue()));
    }

    public @Nonnull DatelessExchange getExchange() {
        return exchange;
    }

    public @Nonnull SortedMap<LocalDate, BigDecimal> getRateValueChronology() {
        return Collections.unmodifiableSortedMap(rateValueChronology);
    }

    public Optional<BigDecimal> getMinValue() {
        // TODO (Performance): This can be lazily-computed and cached
        return rateValueChronology.values()
                .stream()
                .min(BigDecimal::compareTo);
    }

    public Optional<BigDecimal> getMaxValue() {
        // TODO (Performance): This can be lazily-computed and cached
        return rateValueChronology.values()
                .stream()
                .max(BigDecimal::compareTo);
    }

    public Optional<BigDecimal> getAverageValue() {
        // TODO (Performance): This can be lazily-computed and cached
        return rateValueChronology.values()
                .stream()
                .reduce(BigDecimal::add)
                .map(sum -> {
                    // If this lambda expression gets executed at all, that means count is not zero
                    BigDecimal count = BigDecimal.valueOf(rateValueChronology.size());
                    return sum.divide(count, RoundingMode.HALF_EVEN); // Docs say this is called "Banker's rounding", sounds appropriate! :)
                });
    }

    public Optional<BigDecimal> getEarliestValue() {
        return Maps.getFirstValue(rateValueChronology);
    }

    public Optional<BigDecimal> getLatestValue() {
        return Maps.getLastValue(rateValueChronology);
    }

    public int getValueCount() {
        return rateValueChronology.size();
    }
}