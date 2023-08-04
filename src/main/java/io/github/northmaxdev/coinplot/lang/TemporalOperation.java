// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.lang;

import jakarta.annotation.Nonnull;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;

public enum TemporalOperation {
    ADDITION,
    SUBTRACTION;

    // From Temporal::plus JavaDoc (Java SE 20):
    // "Returns: an object of the same type with the specified adjustment made, not null"
    // All standard Java SE implementations seem to adhere to this requirement as well.
    // Therefore, this cast should always be safe.
    @SuppressWarnings("unchecked")
    public <T extends Temporal, A extends TemporalAmount> T applyTo(@Nonnull T temporal, @Nonnull A amount) {
        return (T) switch (this) {
            case ADDITION -> temporal.plus(amount);
            case SUBTRACTION -> temporal.minus(amount);
        };
    }
}
