// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.ResourceFetchFailureException;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.lang.chrono.LocalDateInterval;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Set;

public interface ExchangeRateService {

    Set<ExchangeRate> getExchangeRates(
            @Nullable Currency base,
            Set<Currency> targets,
            @Nonnull LocalDateInterval dateInterval
    ) throws ResourceFetchFailureException;
}
