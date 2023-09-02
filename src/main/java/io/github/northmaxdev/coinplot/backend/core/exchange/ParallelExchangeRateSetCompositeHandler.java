// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Cannot be instantiated directly (package-private), use ExchangeRateSetHandler::parallelComposedOf
final class ParallelExchangeRateSetCompositeHandler extends AbstractExchangeRateSetCompositeHandler {

    // FIXME:
    //  1. Review implementation of the actual multithreading for correctness
    //  2. This should probably implement AutoCloseable for the ExecutorService

    private final ExecutorService executorService;

    public ParallelExchangeRateSetCompositeHandler(@Nullable ExchangeRateSetHandler... children) {
        super(children);
        // Null-safe way to get array length
        int threadCount = getChildren()
                .size();
        executorService = Executors.newFixedThreadPool(threadCount);
    }

    @Override
    public void handle(@Nonnull Set<ExchangeRate> dataset) {
        Objects.requireNonNull(dataset);
        getChildren()
                .stream()
                .map(handler -> (Runnable) () -> ExchangeRateSetHandler.handleIfNotNull(handler, dataset))
                .forEach(executorService::submit);
    }
}
