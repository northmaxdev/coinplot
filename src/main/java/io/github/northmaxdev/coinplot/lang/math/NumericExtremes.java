// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import io.github.northmaxdev.coinplot.lang.SequencedCollections;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.SequencedCollection;

// https://www.statista.com/statistics-glossary/definition/204/extreme_value/
public record NumericExtremes(@Nonnull BigDecimal min, @Nonnull BigDecimal max) {

    // We don't validate min and max against each other because
    // the order might depend on custom Comparator rules.
    public NumericExtremes(@Nonnull BigDecimal min, @Nonnull BigDecimal max) {
        this.min = Objects.requireNonNull(min);
        this.max = Objects.requireNonNull(max);
    }

    public static Optional<NumericExtremes> find(@Nonnull Collection<BigDecimal> values) {
        return find(values, BigDecimal::compareTo);
    }

    public static Optional<NumericExtremes> find(@Nonnull Collection<BigDecimal> values, @Nonnull Comparator<BigDecimal> comparator) {
        Objects.requireNonNull(values);
        Objects.requireNonNull(comparator);

        SequencedCollection<BigDecimal> sortedValues = values.stream()
                .sorted(comparator)
                .toList();

        return SequencedCollections.applyToEndpoints(sortedValues, NumericExtremes::new);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof NumericExtremes that
                && BigDecimals.equalIgnoringScale(this.min, that.min)
                && BigDecimals.equalIgnoringScale(this.max, that.max);
    }

    @Override
    public int hashCode() {
        return BigDecimals.hashIgnoringScale(min) ^ BigDecimals.hashIgnoringScale(max);
    }
}
