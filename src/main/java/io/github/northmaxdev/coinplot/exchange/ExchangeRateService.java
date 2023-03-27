// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.exchange;

import io.github.northmaxdev.coinplot.currency.Currency;
import jakarta.annotation.Nonnull;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface ExchangeRateService {

    List<ExchangeRate> getExchangeRatesBetweenDates(
            @Nonnull Currency base,
            @Nonnull Collection<Currency> targets,
            @Nonnull LocalDate start,
            @Nonnull LocalDate end);
}
