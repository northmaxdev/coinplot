// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import jakarta.annotation.Nonnull;

import java.util.Collection;
import java.util.List;

abstract sealed class AbstractExchangeRateSetCompositeHandler // Package-private
        implements ExchangeRateSetHandler
        permits ParallelExchangeRateSetCompositeHandler, SingleThreadedExchangeRateSetCompositeHandler {

    private final Collection<ExchangeRateSetHandler> children;

    protected AbstractExchangeRateSetCompositeHandler(@Nonnull ExchangeRateSetHandler... children) {
        this.children = List.of(children); // Implicit null-check in List::of
    }

    protected Collection<ExchangeRateSetHandler> getChildren() {
        return children;
    }
}
