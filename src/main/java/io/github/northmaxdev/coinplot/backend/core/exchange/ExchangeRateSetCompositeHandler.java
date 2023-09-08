// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.lang.MoreCollections;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

public final class ExchangeRateSetCompositeHandler implements ExchangeRateSetHandler {

    private final @Nonnull Collection<ExchangeRateSetHandler> children;
    private final boolean parallel;

    public static @Nonnull ExchangeRateSetCompositeHandler singleThreadedOf(@Nullable ExchangeRateSetHandler... handlers) {
        return new ExchangeRateSetCompositeHandler(false, handlers);
    }

    public static @Nonnull ExchangeRateSetCompositeHandler parallelOf(@Nullable ExchangeRateSetHandler... handlers) {
        return new ExchangeRateSetCompositeHandler(true, handlers);
    }

    private ExchangeRateSetCompositeHandler(boolean parallel, @Nullable ExchangeRateSetHandler... children) {
        this.children = MoreCollections.deeplyDenullified(children);
        this.parallel = parallel;
    }

    @Override
    public void handle(@Nonnull Set<ExchangeRate> dataset) {
        Objects.requireNonNull(dataset);

        if (parallel) {
            // TODO: Implement
            throw new UnsupportedOperationException();
        } else {
            children.forEach(child -> child.handle(dataset));
        }
    }
}
