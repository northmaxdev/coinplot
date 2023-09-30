// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import jakarta.annotation.Nonnull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class ExchangeRateSetCompositeHandler implements ExchangeRateSetHandler {

    private final @Nonnull Collection<ExchangeRateSetHandler> children;
    private final boolean parallel;

    public static @Nonnull ExchangeRateSetCompositeHandler singleThreadedOf(@Nonnull ExchangeRateSetHandler... handlers) {
        return new ExchangeRateSetCompositeHandler(false, handlers);
    }

    public static @Nonnull ExchangeRateSetCompositeHandler parallelOf(@Nonnull ExchangeRateSetHandler... handlers) {
        return new ExchangeRateSetCompositeHandler(true, handlers);
    }

    private ExchangeRateSetCompositeHandler(boolean parallel, @Nonnull ExchangeRateSetHandler... children) {
        this.children = List.of(children); // Implicit deep null-check(s)
        this.parallel = parallel;
    }

    @Override
    public void handle(@Nonnull Set<ExchangeRate> dataset) {
        Objects.requireNonNull(dataset);

        if (parallel) {
            // FIXME (Implementation)
            throw new UnsupportedOperationException();
        } else {
            children.forEach(child -> child.handle(dataset));
        }
    }
}
