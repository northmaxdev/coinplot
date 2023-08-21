// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Objects;

// This class contains static utility methods which simply delegate to
// Objects::requireNonNull with pre-made messages for common cases.
public final class NullChecks {

    private NullChecks() {
        throw new UnsupportedOperationException();
    }

    public static <T> @Nonnull T forConfiguration(@Nullable T configuration) {
        return Objects.requireNonNull(configuration, "Configuration object cannot be null");
    }

    public static <T> @Nonnull T forDTO(@Nullable T dto) {
        return Objects.requireNonNull(dto, "DTO cannot be null");
    }
}
