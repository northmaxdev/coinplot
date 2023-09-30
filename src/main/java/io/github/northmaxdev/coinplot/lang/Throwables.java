// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;

import java.util.Objects;

public final class Throwables {

    private Throwables() {
        throw new UnsupportedOperationException();
    }

    public static @Nonnull String getMessage(@Nonnull Throwable t) {
        return getMessage(t, "<no message>");
    }

    public static @Nonnull String getMessage(@Nonnull Throwable t, @Nonnull String placeholderMessage) {
        return Objects.requireNonNullElse(t.getMessage(), placeholderMessage);
    }
}
