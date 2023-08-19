// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.FailedDataFetchException;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.lang.chrono.LocalDateInterval;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Set;

// Semantically not a @FunctionalInterface (additional abstract methods may be added in the future)
public interface ExchangeRateService {

    Set<ExchangeRate> getExchangeRates(@Nullable Currency base,
                                       Set<Currency> targets,
                                       @Nonnull LocalDateInterval dateInterval) throws FailedDataFetchException;
}
