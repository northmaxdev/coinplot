// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

final class IntConstancy extends AbstractNumericConstancy<Integer> { // Package-private

    private final int x;

    public IntConstancy(int x) {
        this.x = x;
    }

    @Override
    public @Nonnull Integer getInitialValue() {
        return x;
    }

    @Override
    public @Nonnull Integer getFinalValue() {
        return x;
    }

    @Override
    public @Nonnull Integer getDifference() {
        return 0;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof IntConstancy that
                && this.x == that.x;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(x);
    }
}
