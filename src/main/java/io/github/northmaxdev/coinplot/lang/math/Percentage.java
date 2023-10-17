// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

// This class deals with percentage values, NOT decimal ones. This means a value of 75.5 represents 75.5%.
// It should be noted that most Java APIs, such as HashMap's load factor or NumberFormat::getPercentInstance,
// expect decimal representations, where a value of 75.5 would represent 7550%.
// The decimalValue() and fromDecimalValue(double) methods may be used for compatibility.
public record Percentage(double value) implements Comparable<Percentage> {

    // More constants for common values may be added in the future
    public static final Percentage ZERO = new Percentage(0);

    private static final double DECIMAL_VALUE_MULTIPLIER = 100;

    public Percentage {
        if (!Double.isFinite(value)) { // Unlike isInfinite, isFinite explicitly documents NaN cases (which it rejects)
            throw new IllegalArgumentException("Percentage value must be a finite number");
        }
    }

    public static @Nonnull Percentage fromDecimalValue(double decimalValue) {
        return new Percentage(decimalValue * DECIMAL_VALUE_MULTIPLIER);
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
        if (!Double.isFinite(before) || !Double.isFinite(after)) {
            throw new IllegalArgumentException("Values must be finite numbers");
        }

        // At this point in time, NaNs and infinities have been filtered out, but zeros are still possible:
        // 0, 0 --> 0%
        // 0, _ --> must be rejected (throw IAE)
        // _, 0 --> calculate % (should be 100% or -100%)
        // _, _ (equal) --> 0%
        // _, _ (unequal) --> calculate %

        if (Double.compare(before, after) == 0) {
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

    public static @Nonnull Percentage ofChange(@Nonnull Number before, @Nonnull Number after) {
        Objects.requireNonNull(before);
        Objects.requireNonNull(after);
        return ofChange(before.doubleValue(), after.doubleValue());
    }

    public double decimalValue() {
        return value / DECIMAL_VALUE_MULTIPLIER;
    }

    public @Nonnull String format() {
        return NumberFormat.getPercentInstance()
                .format(decimalValue());
    }

    public @Nonnull String format(@Nonnull Locale locale) {
        return NumberFormat.getPercentInstance(locale)
                .format(decimalValue());
    }

    @Override
    public @Nonnull String toString() {
        return format();
    }

    @Override
    public int compareTo(@Nonnull Percentage other) {
        return Double.compare(value, other.value);
    }
}
