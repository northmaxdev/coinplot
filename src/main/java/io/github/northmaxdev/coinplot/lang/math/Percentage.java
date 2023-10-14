// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;

import java.text.NumberFormat;
import java.util.Locale;

// This class deals with percentage values, NOT decimal ones. This means a value of 75.5 represents 75.5%.
// It should be noted that most Java APIs, such as HashMap's load factor or NumberFormat::getPercentInstance,
// expect decimal representations, where a value of 75.5 would represent 7550%.
// The decimalValue() and fromDecimalValue(double) methods may be used for compatibility.
public record Percentage(double value) implements Comparable<Percentage> {

    private static final double DECIMAL_MULTIPLIER = 100.0;

    public static @Nonnull Percentage fromDecimalValue(double decimalValue) {
        return new Percentage(decimalValue * DECIMAL_MULTIPLIER);
    }

    public double decimalValue() {
        return value / DECIMAL_MULTIPLIER;
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
