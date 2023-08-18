// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import jakarta.annotation.Nonnull;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Cannot be instantiated directly, use ExchangeRateSetHandler::parallelComposite
final class ParallelExchangeRateSetCompositeHandler extends AbstractExchangeRateSetCompositeHandler {

    // FIXME: Review implementation of the actual multithreading for correctness
    // FIXME: This should probably implement AutoCloseable for the ExecutorService

    private final ExecutorService executorService;

    public ParallelExchangeRateSetCompositeHandler(ExchangeRateSetHandler... children) {
        super(children);
        this.executorService = Executors.newFixedThreadPool(children.length);
    }

    @Override
    public void handle(@Nonnull Set<ExchangeRate> dataset) {
        getChildren()
                .stream()
                .map(handler -> (Runnable) () -> handler.handle(dataset))
                .forEach(executorService::submit);
    }
}
