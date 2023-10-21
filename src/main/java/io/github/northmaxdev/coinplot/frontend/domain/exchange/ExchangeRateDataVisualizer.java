// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRate;
import jakarta.annotation.Nonnull;

import java.util.Set;

// Not marked @FunctionalInterface as additional methods may or may not be added in the future
public interface ExchangeRateDataVisualizer {

    void visualize(@Nonnull Set<ExchangeRate> dataset);
}
