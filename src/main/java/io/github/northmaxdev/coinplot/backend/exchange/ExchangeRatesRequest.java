// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.exchange;

import io.github.northmaxdev.coinplot.backend.currency.Currency;
import io.github.northmaxdev.coinplot.backend.web.request.APIRequest;
import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public interface ExchangeRatesRequest extends APIRequest {

    // A convenient utility method for an operation that is frequently executed by implementations
    static Optional<NameValuePair> joinTargetsToParameter(@Nonnull String parameterName, Collection<Currency> targets) {
        if (targets.isEmpty()) {
            return Optional.empty();
        }

        String joinedCodes = targets.stream()
                .filter(Objects::nonNull) // This may be removed if enforced by implementations
                .map(Currency::getCode)
                .collect(joining(","));

        NameValuePair parameter = new BasicNameValuePair(parameterName, joinedCodes);
        return Optional.of(parameter);
    }
}
