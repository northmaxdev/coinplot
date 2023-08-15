// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import jakarta.annotation.Nonnull;

import java.util.Set;

@FunctionalInterface
public interface ExchangeRateSetHandler {

    void handle(@Nonnull Set<ExchangeRate> dataset);
}
