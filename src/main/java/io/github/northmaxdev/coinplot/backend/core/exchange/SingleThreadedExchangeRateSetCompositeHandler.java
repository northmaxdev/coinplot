// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import jakarta.annotation.Nonnull;

import java.util.Set;

// Cannot be instantiated directly. Use ExchangeRateSetHandler::of
final class SingleThreadedExchangeRateSetCompositeHandler extends AbstractExchangeRateSetCompositeHandler {

    public SingleThreadedExchangeRateSetCompositeHandler(ExchangeRateSetHandler... children) {
        super(children);
    }

    @Override
    public void handle(@Nonnull Set<ExchangeRate> dataset) {
        for (ExchangeRateSetHandler child : getChildren()) {
            child.handle(dataset);
        }
    }
}
