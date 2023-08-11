// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.chrono;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.Period;
import java.util.Objects;

public final class Periods {

    private Periods() {
        throw new UnsupportedOperationException();
    }

    public static @Nonnull Period zeroIfNull(@Nullable Period p) {
        return Objects.requireNonNullElse(p, Period.ZERO);
    }
}
