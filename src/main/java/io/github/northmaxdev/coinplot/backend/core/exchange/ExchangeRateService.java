// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.ResourceFetchException;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.lang.LocalDateInterval;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Set;

public interface ExchangeRateService {

    Set<ExchangeRate> getExchangeRates(
            @Nullable Currency base,
            Set<Currency> targets,
            @Nonnull LocalDateInterval dateInterval
    ) throws ResourceFetchException;

    default Set<ExchangeRate> getExchangeRates(@Nonnull ExchangeRateSetRequest request) throws ResourceFetchException {
        // We honor the convention of only using java.util.Optional as a method's return type,
        // never as a parameter or a field.
        @Nullable Currency base = request.getBase()
                .orElse(null);
        return getExchangeRates(base, request.getTargets(), request.getDateInterval());
    }
}
