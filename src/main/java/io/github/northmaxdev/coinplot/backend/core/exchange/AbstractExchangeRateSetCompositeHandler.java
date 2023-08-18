// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import java.util.Collection;
import java.util.List;

abstract sealed class AbstractExchangeRateSetCompositeHandler // Package-private
        implements ExchangeRateSetHandler
        permits ParallelExchangeRateSetCompositeHandler, SingleThreadedExchangeRateSetCompositeHandler {

    private final Collection<ExchangeRateSetHandler> children;

    protected AbstractExchangeRateSetCompositeHandler(ExchangeRateSetHandler... children) {
        this.children = List.of(children);
    }

    protected Collection<ExchangeRateSetHandler> getChildren() {
        return children;
    }
}
