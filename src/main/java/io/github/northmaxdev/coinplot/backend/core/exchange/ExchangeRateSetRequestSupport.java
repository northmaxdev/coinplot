// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import jakarta.annotation.Nonnull;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.joining;

// Intended to be used ONLY for implementations of ExchangeRateSetRequest.
public final class ExchangeRateSetRequestSupport {

    private ExchangeRateSetRequestSupport() {}

    public static boolean areBasicPropertiesEqual(
            @Nonnull ExchangeRateSetRequest a,
            @Nonnull ExchangeRateSetRequest b) {
        return Objects.equals(a.getBase(), b.getBase())
                && Objects.equals(a.getTargets(), b.getTargets())
                && Objects.equals(a.getDateInterval(), b.getDateInterval());
    }

    public static int hashBasicProperties(@Nonnull ExchangeRateSetRequest request) {
        return Objects.hash(request.getBase(), request.getTargets(), request.getDateInterval());
    }

    public static Optional<String> joinTargetCodes(@Nonnull ExchangeRateSetRequest request) {
        Set<Currency> targets = request.getTargets();

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
}
