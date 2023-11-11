// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import io.github.northmaxdev.coinplot.lang.Doubles;
import io.github.northmaxdev.coinplot.lang.Iterables;
import io.github.northmaxdev.coinplot.lang.Localizable;
import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Predicate;

// This class deals with percentage values, NOT decimal ones. This means a value of 75.5 represents 75.5%.
// It should be noted that most Java APIs, such as HashMap's load factor or NumberFormat::getPercentInstance,
// expect decimal representations, where a value of 75.5 would represent 7550%.
// The decimalValue() and fromDecimalValue(double) methods may be used for compatibility.
// Please note that methods like Percentage::ofChange may yield inaccurate results.
public record Percentage(double value) implements Comparable<Percentage>, Localizable {

    // More constants for common values may be added in the future
    public static final Percentage ZERO = new Percentage(0);

    private static final double DECIMAL_VALUE_MULTIPLIER = 100;
    private static final BigDecimal BIG_DECIMAL_VALUE_MULTIPLIER = BigDecimal.valueOf(DECIMAL_VALUE_MULTIPLIER);
    private static final int FORMAT_MAX_FRACTION_DIGITS = 4;

    public Percentage(double value) {
        this.value = Doubles.requireFinite(value);
    }

    public Percentage(@Nonnull BigDecimal value) {
        this(value.doubleValue());
    }

    public static @Nonnull Percentage fromDecimalValue(double decimalValue) {
        return new Percentage(decimalValue * DECIMAL_VALUE_MULTIPLIER);
    }

    public static @Nonnull Percentage fromDecimalValue(@Nonnull BigDecimal decimalValue) {
        Objects.requireNonNull(decimalValue);
        return new Percentage(decimalValue.multiply(BIG_DECIMAL_VALUE_MULTIPLIER));
    }

    // +--------+-------+----------+------------------------------------------+
    // | Before | After | Change % | Case description                         |
    // +--------+-------+----------+------------------------------------------+
    // |  2.5   |  3    |  20%     | An increase between two positives        |
    // |  4     |  3.5  | -12.5%   | A decrease between two positives         |
    // |  5.96  |  5.96 |  0%      | No change between two positives          |
    // |  0.5   | -0.25 | -150%    | A decrease from positive to negative     |
    // |  2758  |  0    | -100%    | A decrease from positive to zero         |
    // | -5     |  2.5  |  150%    | An increase from negative to positive    |
    // | -1     | -0.75 |  25%     | An increase between two negatives        |
    // | -0.375 | -0.45 | -20%     | A decrease between two negatives         |
    // | -0.17  | -0.17 |  0%      | No change between two negatives          |
    // | -99999 |  0    |  100%    | An increase from negative to zero        |
    // |  0     |  0    |  0%      | No change between two zeroes (edge case) |
    // +--------+-------+----------+------------------------------------------+
    // It is not allowed for the 'before' value to be zero, with the only
    // exception being a specific edge case of both values being zero, in which
    // case the method returns 0% - just like in any other scenario where both
    // values are equal. NaN and infinities are not allowed.
    public static @Nonnull Percentage ofChange(double before, double after) {
        Doubles.requireFinite(before);
        Doubles.requireFinite(after);

        // At this point in time, NaNs and infinities have been filtered out, but zeros are still possible:
        // 0, 0 --> 0%
        // 0, _ --> must be rejected (throw IAE)
        // _, 0 --> calculate % (should be 100% or -100%)
        // _, _ (equal) --> 0%
        // _, _ (unequal) --> calculate %

        if (Doubles.equals(before, after)) {
            return ZERO;
        }

        // At this point in time, equal arguments have been filtered out:
        // 0, _ --> must be rejected (throw IAE)
        // _, 0 --> calculate % (should be 100% or -100%)
        // _, _ (unequal) --> calculate %

        // Source: https://www.calculatorsoup.com/calculators/algebra/percentage-increase-calculator.php
        double calculatedDecimalValue = (after - before) / Math.abs(before);

        // At this point in time, possible result options are:
        // 0, _ --> +Inf or -Inf (since in this case it's division by 0) --> eventual IAE by the canonical constructor (not fail-fast)
        // _, 0 --> OK calculation
        // _, _ (unequal) --> OK calculation

        return fromDecimalValue(calculatedDecimalValue);
    }

    public static @Nonnull Percentage ofChange(long before, long after) {
        if (before == after) {
            return ZERO;
        }

        // At this point in time, equal arguments have been filtered out:
        // 0, _ --> must be rejected (throw IAE)
        // _, 0 --> calculate % (should be 100% or -100%)
        // _, _ (unequal) --> calculate %

        if (before == 0L) {
            throw new IllegalArgumentException("Cannot calculate change from zero unless both operands are zero");
        }

        // Source: https://www.calculatorsoup.com/calculators/algebra/percentage-increase-calculator.php
        double calculatedDecimalValue = (double) (after - before) / Math.abs(before);
        return fromDecimalValue(calculatedDecimalValue);
    }

    public static @Nonnull Percentage ofChange(@Nonnull BigDecimal before, @Nonnull BigDecimal after) {
        Objects.requireNonNull(before);
        Objects.requireNonNull(after);

        if (BigDecimals.equalIgnoringScale(before, after)) {
            return ZERO;
        }

        // At this point in time, equal arguments have been filtered out:
        // 0, _ --> must be rejected (throw IAE)
        // _, 0 --> calculate % (should be 100% or -100%)
        // _, _ (unequal) --> calculate %

        if (BigDecimals.equalsZeroIgnoringScale(before)) {
            throw new IllegalArgumentException("Cannot calculate change from zero unless both operands are zero");
        }

        // Source: https://www.calculatorsoup.com/calculators/algebra/percentage-increase-calculator.php
        BigDecimal numerator = after.subtract(before);
        BigDecimal denominator = before.abs();
        BigDecimal calculatedDecimalValue = numerator.divide(denominator, RoundingMode.HALF_UP);

        return fromDecimalValue(calculatedDecimalValue);
    }

    public static <T> @Nonnull Percentage ofMatchingPredicate(@Nonnull Iterable<T> iterable, @Nonnull Predicate<T> predicate) {
        Objects.requireNonNull(iterable);
        Objects.requireNonNull(predicate);

        // Consider somehow doing a protective copy against potential TOCTOU issues

        if (Iterables.isEmpty(iterable)) {
            return ZERO;
        }

        int totalCount = Iterables.size(iterable);
        int predicateMatchCount = (int) Iterables.stream(iterable)
                .filter(predicate)
                .count();

        return fromDecimalValue((double) predicateMatchCount / totalCount);
    }

    public double decimalValue() {
        return value / DECIMAL_VALUE_MULTIPLIER;
    }

    @Override
    public @Nonnull String toString() {
        NumberFormat fmt = NumberFormat.getPercentInstance();
        fmt.setMaximumFractionDigits(FORMAT_MAX_FRACTION_DIGITS);
        return fmt.format(decimalValue());
    }

    @Override
    public @Nonnull String toLocalizedString(@Nonnull Locale locale) {
        NumberFormat fmt = NumberFormat.getPercentInstance(locale);
        fmt.setMaximumFractionDigits(FORMAT_MAX_FRACTION_DIGITS);
        return fmt.format(decimalValue());
    }

    @Override
    public int compareTo(@Nonnull Percentage other) {
        return Double.compare(value, other.value);
    }
}
