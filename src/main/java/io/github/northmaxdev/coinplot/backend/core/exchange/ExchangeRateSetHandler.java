// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Set;

@FunctionalInterface
public interface ExchangeRateSetHandler {

    void handle(Set<ExchangeRate> dataset);

    // Imitation of Kotlin/C# null-safe method calls
    static void handleIfNotNull(@Nullable ExchangeRateSetHandler handler, Set<ExchangeRate> dataset) {
        if (handler != null) {
            handler.handle(dataset);
        }
    }

    static @Nonnull ExchangeRateSetHandler composedOf(@Nonnull ExchangeRateSetHandler... handlers) {
        return new SingleThreadedExchangeRateSetCompositeHandler(handlers);
    }

    static @Nonnull ExchangeRateSetHandler parallelComposedOf(@Nonnull ExchangeRateSetHandler... handlers) {
        return new ParallelExchangeRateSetCompositeHandler(handlers);
    }
}
