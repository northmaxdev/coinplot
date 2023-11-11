// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

final class LongChange extends AbstractNumericChange<Long> { // Package-private

    private final long x1;
    private final long x2;

    public LongChange(long x1, long x2) {
        this.x1 = x1;
        this.x2 = x2;
    }

    @Override
    public @Nonnull Long getInitialValue() {
        return x1;
    }

    @Override
    public @Nonnull Long getFinalValue() {
        return x2;
    }

    @Override
    public @Nonnull Long getDifference() {
        return x2 - x1;
    }

    @Override
    public @Nonnull Percentage getPercentage() {
        if (isPercentageCalculable()) {
            return Percentage.ofChange(x1, x2);
        } else {
            throw new IllegalStateException("Change percentage is incalculable");
        }
    }

    @Override
    public boolean isPercentageIncalculable() {
        return x1 == 0L && x2 != 0L;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof LongChange that
                && this.x1 == that.x1
                && this.x2 == that.x2;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(x1) ^ Long.hashCode(x2);
    }
}
