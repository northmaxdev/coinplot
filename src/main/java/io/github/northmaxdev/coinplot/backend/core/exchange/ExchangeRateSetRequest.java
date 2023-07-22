// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.web.request.APIRequest;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.joining;

// T represents the exact temporal type used for specifying start/end boundaries.
public interface ExchangeRateSetRequest<T> extends APIRequest {

    Optional<Currency> getBase();

    Set<Currency> getTargets();

    // Just a common operation, used mostly as an implementation detail.
    default Optional<String> getJoinedTargetCodes() {
        Set<Currency> targets = getTargets();

        if (targets.isEmpty()) {
            return Optional.empty();
        }

        String s = targets.stream()
                .filter(Objects::nonNull) // Just in case
                .map(Currency::getCode)
                .collect(joining(","));

        return s.isEmpty()
                ? Optional.empty()
                : Optional.of(s);
    }

    Optional<T> getStart();

    Optional<T> getEnd();
}
