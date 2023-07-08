// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.exchange;

import io.github.northmaxdev.coinplot.backend.currency.Currency;
import jakarta.annotation.Nonnull;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

final class ExchangeRatesRequestUtils { // Package-private

    private ExchangeRatesRequestUtils() {
    }

    static Optional<NameValuePair> joinTargetsToParameter(
            @Nonnull String parameterName,
            Collection<Currency> targets) {
        if (targets.isEmpty()) {
            return Optional.empty();
        }

        String joinedCodes = targets.stream()
                .filter(Objects::nonNull) // FIXME: There should be no need to explicitly do this
                .map(Currency::getCode)
                .collect(joining(","));

        NameValuePair parameter = new BasicNameValuePair(parameterName, joinedCodes);
        return Optional.of(parameter);
    }
}
