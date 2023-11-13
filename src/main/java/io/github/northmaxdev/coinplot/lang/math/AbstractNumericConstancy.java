// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;

// Represents a NumericChange between two equal values, which is, in fact, not a change, but a "constancy".
// In other words, this is a separate line of Java classes for an edge case.
abstract class AbstractNumericConstancy<T extends Number> extends AbstractNumericChange<T> { // Package-private

    @Override
    public final @Nonnull Percentage getPercentage() {
        return Percentage.ZERO;
    }

    @Override
    public final boolean isPercentageIncalculable() {
        return false;
    }

    @Override
    public final boolean isPercentageCalculable() {
        return true;
    }
}
