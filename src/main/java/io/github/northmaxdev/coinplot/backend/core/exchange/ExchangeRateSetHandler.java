// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Objects;
import java.util.Set;

@FunctionalInterface
public interface ExchangeRateSetHandler {

    void handle(@Nonnull Set<ExchangeRate> dataset);

    // Imitation of Kotlin/C# null-safe method calls
    static void handleIfNotNull(@Nullable ExchangeRateSetHandler handler, @Nonnull Set<ExchangeRate> dataset) {
        Objects.requireNonNull(dataset);
        if (handler != null) {
            handler.handle(dataset);
        }
    }

    static @Nonnull ExchangeRateSetHandler composedOf(@Nullable ExchangeRateSetHandler... handlers) {
        return new SingleThreadedExchangeRateSetCompositeHandler(handlers);
    }

    static @Nonnull ExchangeRateSetHandler parallelComposedOf(@Nullable ExchangeRateSetHandler... handlers) {
        return new ParallelExchangeRateSetCompositeHandler(handlers);
    }
}
