// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import io.github.northmaxdev.coinplot.lang.Iterables;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.function.Predicate;

// This class deals with percentage values, NOT decimal ones. This means a value of 75.5 represents 75.5%.
// It should be noted that most Java APIs, such as HashMap's load factor or NumberFormat::getPercentInstance,
// expect decimal representations, where a value of 75.5 would represent 7550%.
public final class Percentage implements Comparable<Percentage> {

    public static final Percentage ZERO = new Percentage(BigDecimal.ZERO);
    public static final Percentage HUNDRED = new Percentage(BigDecimals.HUNDRED);

    // Let's say we're calculating a percentage of change between X and Y which is expected to be 12.5%.
    // If the arithmetic operations of the calculation yield a decimal value, which in this case would be 0.125,
    // the MathContext in use would have to have a precision of at least 3 in order to avoid serious rounding errors.
    // Setting the precision to a value of 3 solves the issue for decimal arithmetic,
    // but limits the actual percentage value to a precision of 1, as in the case of 12.5% and 0.125.
    // Thus, if we want to allow percentage values to have a precision of 3
    // (an arbitrarily chosen number that seems reasonable for percentages in most use-cases),
    // the actual MathContext minimum precision for decimal arithmetic is therefore x+2, where x>=3.
    private static final MathContext DEFAULT_DECIMAL_ARITHMETIC_MATH_CONTEXT = new MathContext(5, RoundingMode.HALF_UP);

    private final @Nonnull BigDecimal value;

    private Percentage(@Nonnull BigDecimal value) {
        this.value = value;
    }

    public static @Nonnull Percentage of(@Nonnull BigDecimal value) {
        Objects.requireNonNull(value);
        return new Percentage(value);
    }

    public static @Nonnull Percentage of(long value) {
        return new Percentage(BigDecimal.valueOf(value));
    }

    public static @Nonnull Percentage of(double value) {
        if (Double.isFinite(value)) {
            return new Percentage(BigDecimal.valueOf(value));
        } else {
            throw new IllegalArgumentException("Value must be finite");
        }
    }

    public static @Nonnull Percentage fromDecimalValue(@Nonnull BigDecimal decimalValue) {
        Objects.requireNonNull(decimalValue);
        BigDecimal value = decimalValue.multiply(BigDecimals.HUNDRED);
        return new Percentage(value);
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
    // values are equal.
    public static @Nonnull Percentage ofChange(
            @Nonnull BigDecimal before,
            @Nonnull BigDecimal after,
            // Assuming the desired precision for percentage values is x,
            // the MathContext "precision" property must be equal to x+2.
            // This is to account for the decimal arithmetic this method executes.
            @Nonnull MathContext mathContext) {

        Objects.requireNonNull(before);
        Objects.requireNonNull(after);
        Objects.requireNonNull(mathContext);

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
        BigDecimal calculatedDecimalValue = numerator.divide(denominator, mathContext);

        return fromDecimalValue(calculatedDecimalValue);
    }

    public static @Nonnull Percentage ofChange(@Nonnull BigDecimal before, @Nonnull BigDecimal after) {
        return ofChange(before, after, DEFAULT_DECIMAL_ARITHMETIC_MATH_CONTEXT);
    }

    public static @Nonnull Percentage ofChange(long before, long after) {
        // Quick optimization
        if (before == after) {
            return ZERO;
        }

        return ofChange(BigDecimal.valueOf(before), BigDecimal.valueOf(after));
    }

    public static @Nonnull Percentage ofChange(double before, double after) {
        return ofChange(BigDecimal.valueOf(before), BigDecimal.valueOf(after));
    }

    public static <T> @Nonnull Percentage ofMatchingPredicate(
            @Nonnull Iterable<T> iterable,
            @Nonnull Predicate<T> predicate,
            // Assuming the desired precision for percentage values is x,
            // the MathContext "precision" property must be equal to x+2.
            // This is to account for the decimal arithmetic this method executes.
            @Nonnull MathContext mathContext) {

        Objects.requireNonNull(iterable);
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(mathContext);

        // Consider somehow doing a protective copy against potential TOCTOU issues

        // Mandatory edge-case to avoid a division of zero by zero later on
        if (Iterables.isEmpty(iterable)) {
            return ZERO;
        }

        long totalCount = Iterables.size(iterable);
        long predicateMatchCount = Iterables.stream(iterable)
                .filter(predicate)
                .count();

        // Optional edge-case for optimization
        if (predicateMatchCount == totalCount) {
            return HUNDRED;
        }

        BigDecimal numerator = BigDecimal.valueOf(predicateMatchCount);
        BigDecimal denominator = BigDecimal.valueOf(totalCount);
        BigDecimal calculatedDecimalValue = numerator.divide(denominator, mathContext);

        return fromDecimalValue(calculatedDecimalValue);
    }

    public static <T> @Nonnull Percentage ofMatchingPredicate(
            @Nonnull Iterable<T> iterable,
            @Nonnull Predicate<T> predicate) {
        return ofMatchingPredicate(iterable, predicate, DEFAULT_DECIMAL_ARITHMETIC_MATH_CONTEXT);
    }

    public @Nonnull BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof Percentage that
                && BigDecimals.equalIgnoringScale(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return BigDecimals.hashIgnoringScale(value);
    }

    @Override
    public @Nonnull String toString() {
        // Consider NumberFormat.getPercentInstance()
        return value.toPlainString() + '%';
    }

    @Override
    public int compareTo(@Nonnull Percentage other) {
        return value.compareTo(other.value);
    }
}
