// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange.impl;

import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateSetRequest;
import jakarta.annotation.Nonnull;

import java.util.Objects;

public final class ExchangeRateSetRequests {

    private ExchangeRateSetRequests() {}

    public static boolean basicPropertiesAreEqual(
            @Nonnull ExchangeRateSetRequest a,
            @Nonnull ExchangeRateSetRequest b) {
        return Objects.equals(a.getBase(), b.getBase())
                && Objects.equals(a.getTargets(), b.getTargets())
                && Objects.equals(a.getDateInterval(), b.getDateInterval());
    }

    public static int hashBasicProperties(@Nonnull ExchangeRateSetRequest request) {
        return Objects.hash(request.getBase(), request.getTargets(), request.getDateInterval());
    }
}
