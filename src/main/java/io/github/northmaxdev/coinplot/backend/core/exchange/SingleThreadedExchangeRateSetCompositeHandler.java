// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Set;

// Cannot be instantiated directly (package-private), use ExchangeRateSetHandler::composedOf
final class SingleThreadedExchangeRateSetCompositeHandler extends AbstractExchangeRateSetCompositeHandler {

    public SingleThreadedExchangeRateSetCompositeHandler(@Nullable ExchangeRateSetHandler... children) {
        super(children);
    }

    @Override
    public void handle(@Nonnull Set<ExchangeRate> dataset) {
        getChildren().forEach(child -> ExchangeRateSetHandler.handleIfNotNull(child, dataset));
    }
}
