// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.lang.Maps;
import io.github.northmaxdev.coinplot.lang.SequencedCollections;
import io.github.northmaxdev.coinplot.lang.math.NumericChange;
import io.github.northmaxdev.coinplot.lang.math.NumericExtremes;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toUnmodifiableMap;

// The ExchangeRate edition of ExchangeBatch (see the latter's source file for more info).
// Unlike ExchangeBatch, this class spans over a single target currency only,
// thus representing a more atomic subset of exchange rate data.
public final class ExchangeRateBatch {

    public static final Comparator<ExchangeRateBatch> SIZE_BASED_ORDER = Comparator.comparingInt(ExchangeRateBatch::size);

    private final @Nonnull DatelessExchange exchange;
    private final @Nonnull Map<LocalDate, BigDecimal> rates;

    private ExchangeRateBatch(@Nonnull DatelessExchange exchange, @Nonnull Map<LocalDate, BigDecimal> rates) {
        this.exchange = Objects.requireNonNull(exchange);
        this.rates = Objects.requireNonNull(rates);
    }

    public static @Nonnull Set<ExchangeRateBatch> multipleFromDataset(@Nonnull Set<ExchangeRate> dataset) {
        Map<DatelessExchange, Map<LocalDate, BigDecimal>> groups = dataset.stream()
                // Alternative downstream Collector: toMap(..., ..., (a, b) -> a, TreeMap::new)
                // This may or may not give a performance boost if methods like getValueTimeline() are accessed frequently.
                .collect(groupingBy(DatelessExchange::of, toUnmodifiableMap(ExchangeRate::getDate, ExchangeRate::getValue)));

        return Maps.mapToSet(groups, ExchangeRateBatch::new);
    }

    public @Nonnull DatelessExchange getExchange() {
        return exchange;
    }

    public @Nonnull Map<LocalDate, BigDecimal> getRates() {
        return Collections.unmodifiableMap(rates);
    }

    public @Nonnull SortedMap<LocalDate, BigDecimal> getRateTimeline() {
        SortedMap<LocalDate, BigDecimal> timeline = rates instanceof SortedMap<LocalDate, BigDecimal> sortedMap
                ? sortedMap
                : new TreeMap<>(rates);
        return Collections.unmodifiableSortedMap(timeline);
    }

    public Optional<BigDecimal> getLatestValue() {
        SortedMap<LocalDate, BigDecimal> timeline = getRateTimeline();
        return SequencedCollections.lastElement(timeline.sequencedValues());
    }

    public Optional<NumericChange> getLatestChange() {
        SortedMap<LocalDate, BigDecimal> timeline = getRateTimeline();
        return SequencedCollections.applyToLastTwo(timeline.sequencedValues(), NumericChange::of);
    }

    public Optional<NumericExtremes> getValueExtremes() {
        return NumericExtremes.find(rates.values());
    }

    // Methods stream() and toSet() (like the ones in ExchangeBatch)
    // may be added in the future if they're needed

    public int size() {
        return rates.size();
    }

    @Override
    public int hashCode() {
        return Objects.hash(exchange, rates);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof ExchangeRateBatch that
                && Objects.equals(this.exchange, that.exchange)
                && Objects.equals(this.rates, that.rates);
    }

    @Override
    public @Nonnull String toString() {
        return "[exchange: " + exchange + ", size: " + rates.size() + ']';
    }
}
