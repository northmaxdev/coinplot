// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.FailedDataFetchException;
import jakarta.annotation.Nonnull;

import java.util.Set;

// Semantically not a @FunctionalInterface (additional abstract methods may be added in the future)
public interface ExchangeRateService {

    @Nonnull Set<ExchangeRate> getExchangeRates(@Nonnull ExchangeBatch exchanges) throws FailedDataFetchException;
}
