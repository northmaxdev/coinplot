// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.web.request.APIRequest;
import io.github.northmaxdev.coinplot.lang.chrono.LocalDateInterval;
import jakarta.annotation.Nonnull;

import java.util.Set;

public interface ExchangeRateSetRequest extends APIRequest {

    @Nonnull Currency getBase();

    // Must not be empty and must not contain nulls!
    @Nonnull Set<Currency> getTargets();

    @Nonnull LocalDateInterval getDateInterval();
}
