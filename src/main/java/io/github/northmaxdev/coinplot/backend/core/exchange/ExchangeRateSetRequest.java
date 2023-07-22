// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.web.request.APIRequest;

import java.util.Optional;
import java.util.Set;

// T represents the exact temporal type used for specifying start/end boundaries.
public interface ExchangeRateSetRequest<T> extends APIRequest {

    Optional<Currency> getBase();

    Set<Currency> getTargets();

    Optional<T> getStart();

    Optional<T> getEnd();
}
