// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import jakarta.annotation.Nonnull;

import java.util.Set;

// Semantically not a @FunctionalInterface (additional abstract methods may be added in the future)
public interface ExchangeRateService {

    @Nonnull Set<ExchangeRate> getAvailableExchangeRates(@Nonnull ExchangeBatch exchangeBatch);
}
