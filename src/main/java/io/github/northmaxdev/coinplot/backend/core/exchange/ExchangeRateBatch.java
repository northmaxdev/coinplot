// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.lang.CollectionUtilities;
import io.github.northmaxdev.coinplot.lang.Pair;
import io.github.northmaxdev.coinplot.lang.math.BigDecimals;
import io.github.northmaxdev.coinplot.lang.math.Percentage;
import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.SequencedCollection;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toUnmodifiableMap;
import static java.util.stream.Collectors.toUnmodifiableSet;

// The ExchangeRate edition of ExchangeBatch (see the latter's source file for more info).
// Unlike ExchangeBatch, this class spans over a single target currency only,
// thus representing a more atomic subset of exchange rate data.
public final class ExchangeRateBatch {

    public static final Comparator<ExchangeRateBatch> SIZE_BASED_ORDER = Comparator.comparingInt(ExchangeRateBatch::size);

    private final @Nonnull DatelessExchange exchange;
    private final @Nonnull Map<LocalDate, BigDecimal> values;

    private ExchangeRateBatch(@Nonnull DatelessExchange exchange, @Nonnull Map<LocalDate, BigDecimal> values) {
        this.exchange = Objects.requireNonNull(exchange);
        this.values = Objects.requireNonNull(values);
    }

    public static @Nonnull Set<ExchangeRateBatch> multipleFromDataset(@Nonnull Set<ExchangeRate> dataset) {
        return dataset.stream()
                // Alternative downstream Collector: toMap(..., ..., (a, b) -> a, TreeMap::new)
                // This may or may not give a performance boost if methods like getValueTimeline() are accessed frequently.
                .collect(groupingBy(DatelessExchange::of, toUnmodifiableMap(ExchangeRate::getDate, ExchangeRate::getValue)))
                .entrySet()
                .stream()
                .map(entry -> new ExchangeRateBatch(entry.getKey(), entry.getValue()))
                .collect(toUnmodifiableSet());
    }

    public @Nonnull DatelessExchange getExchange() {
        return exchange;
    }

    public @Nonnull Map<LocalDate, BigDecimal> getValues() {
        return Collections.unmodifiableMap(values);
    }

    public @Nonnull SortedMap<LocalDate, BigDecimal> getValueTimeline() {
        SortedMap<LocalDate, BigDecimal> timeline = values instanceof SortedMap<LocalDate, BigDecimal> m
                ? m
                : new TreeMap<>(values);
        return Collections.unmodifiableSortedMap(timeline);
    }

    public Optional<Percentage> getLatestChangePercentage() {
        SortedMap<LocalDate, BigDecimal> timeline = getValueTimeline();
        return CollectionUtilities.lastTwoElements(timeline.sequencedValues())
                .filter(twoLatestValues -> twoLatestValues.firstMatches(x -> BigDecimals.equalIgnoringScale(x, BigDecimal.ZERO)))
                .map(Percentage::ofChange);
    }

    // The definition of "extremes" is taken from the following web resource:
    // https://www.statista.com/statistics-glossary/definition/204/extreme_value/
    public Optional<Pair<BigDecimal>> getValueExtremes() {
        SequencedCollection<BigDecimal> sortedValues = values.values()
                .stream()
                .sorted()
                .toList();

        return CollectionUtilities.endpoints(sortedValues);
    }

    // TODO:
    //  Methods stream() and toSet() (like the ones in ExchangeBatch)
    //  may be added in the future if they're needed

    public int size() {
        return values.size();
    }

    @Override
    public int hashCode() {
        return Objects.hash(exchange, values);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ExchangeRateBatch that
                && Objects.equals(this.exchange, that.exchange)
                && Objects.equals(this.values, that.values);
    }

    @Override
    public @Nonnull String toString() {
        return "[exchange: " + exchange + ", size: " + values.size() + ']';
    }
}
