// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.lang.MoreCollections;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Collection;

abstract sealed class AbstractExchangeRateSetCompositeHandler // Package-private
        implements ExchangeRateSetHandler
        permits ParallelExchangeRateSetCompositeHandler, SingleThreadedExchangeRateSetCompositeHandler {

    private final @Nonnull Collection<ExchangeRateSetHandler> children;

    protected AbstractExchangeRateSetCompositeHandler(@Nullable ExchangeRateSetHandler... children) {
        this.children = MoreCollections.emptyIfNull(children);
    }

    protected @Nonnull Collection<ExchangeRateSetHandler> getChildren() {
        return children;
    }
}
