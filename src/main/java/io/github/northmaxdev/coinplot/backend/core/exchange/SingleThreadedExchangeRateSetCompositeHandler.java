// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import jakarta.annotation.Nonnull;

import java.util.Set;

// Cannot be instantiated directly (package-private), use ExchangeRateSetHandler::composedOf
final class SingleThreadedExchangeRateSetCompositeHandler extends AbstractExchangeRateSetCompositeHandler {

    public SingleThreadedExchangeRateSetCompositeHandler(@Nonnull ExchangeRateSetHandler... children) {
        super(children); // Null-check in super
    }

    @Override
    public void handle(Set<ExchangeRate> dataset) {
        for (ExchangeRateSetHandler child : getChildren()) {
            ExchangeRateSetHandler.handleIfNotNull(child, dataset); // Null-safety because why not?
        }
    }
}
