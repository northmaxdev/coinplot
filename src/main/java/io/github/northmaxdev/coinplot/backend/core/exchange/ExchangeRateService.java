// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import jakarta.annotation.Nonnull;

import java.util.Set;

// Semantically not a @FunctionalInterface (additional abstract methods may be added in the future)
public interface ExchangeRateService {

    ExchangeRateService NO_OP = new ExchangeRateService() {

        @Override
        public @Nonnull Set<ExchangeRate> getAvailableExchangeRates(@Nonnull ExchangeBatch exchangeBatch) {
            return Set.of();
        }
    };

    @Nonnull Set<ExchangeRate> getAvailableExchangeRates(@Nonnull ExchangeBatch exchangeBatch);
}
