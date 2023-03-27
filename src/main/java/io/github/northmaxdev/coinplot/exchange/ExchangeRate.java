// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.exchange;

import io.github.northmaxdev.coinplot.currency.Currency;
import jakarta.annotation.Nonnull;

import java.time.LocalDate;

public record ExchangeRate(
        @Nonnull Currency base,
        @Nonnull Currency target,
        @Nonnull LocalDate date,
        double value) {}
