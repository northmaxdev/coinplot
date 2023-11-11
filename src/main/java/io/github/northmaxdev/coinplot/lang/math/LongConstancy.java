// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.math;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

final class LongConstancy extends AbstractNumericConstancy<Long> { // Package-private

    private final long x;

    public LongConstancy(long x) {
        this.x = x;
    }

    @Override
    public @Nonnull Long getInitialValue() {
        return x;
    }

    @Override
    public @Nonnull Long getFinalValue() {
        return x;
    }

    @Override
    public @Nonnull Long getDifference() {
        return 0L;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof LongConstancy that
                && this.x == that.x;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(x);
    }
}
