// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.web.request.APIRequest;
import io.github.northmaxdev.coinplot.lang.LocalDateInterval;
import jakarta.annotation.Nonnull;

import java.util.Optional;
import java.util.Set;

public interface ExchangeRateSetRequest extends APIRequest {

    Optional<Currency> getBase();

    Set<Currency> getTargets();

    @Nonnull LocalDateInterval getDateInterval();
}
