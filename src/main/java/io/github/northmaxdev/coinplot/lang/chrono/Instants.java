// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang.chrono;

import jakarta.annotation.Nonnull;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.function.Supplier;

public final class Instants {

    private Instants() {
        throw new UnsupportedOperationException();
    }

    public static @Nonnull Instant toInstant(@Nonnull LocalDate localDate) {
        return localDate.atStartOfDay()
                .toInstant(ZoneOffset.UTC);
    }

    public static @Nonnull Instant toInstant(@Nonnull Supplier<LocalDate> localDateSupplier) {
        Objects.requireNonNull(localDateSupplier);
        return toInstant(localDateSupplier.get());
    }
}
