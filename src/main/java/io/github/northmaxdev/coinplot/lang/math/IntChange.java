// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

final class IntChange extends AbstractNumericChange<Integer> { // Package-private

    private final int x1;
    private final int x2;

    public IntChange(int x1, int x2) {
        this.x1 = x1;
        this.x2 = x2;
    }

    @Override
    public @Nonnull Integer getInitialValue() {
        return x1;
    }

    @Override
    public @Nonnull Integer getFinalValue() {
        return x2;
    }

    @Override
    public @Nonnull Integer getDifference() {
        return x2 - x1;
    }

    @Override
    public @Nonnull Percentage getPercentage() {
        if (isPercentageCalculable()) {
            return Percentage.ofChange(x1, x2);
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean isPercentageIncalculable() {
        return x1 == 0 && x2 != 0;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof IntChange that
                && this.x1 == that.x1
                && this.x2 == that.x2;
    }

    // TODO:
    //  Consider overriding hashCode as follows:
    //  Integer.hashCode(x1) ^ Integer.hashCode(x2)
    //  Might be substantially more performant.
}
