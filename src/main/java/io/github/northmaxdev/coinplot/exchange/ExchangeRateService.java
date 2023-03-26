// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.exchange;

import io.github.northmaxdev.coinplot.currency.Currency;
import jakarta.annotation.Nonnull;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeRateService {

    List<ExchangeRate> getExchangeRatesBetweenDates(
            @Nonnull Currency baseCurrency,
            @Nonnull Currency targetCurrency,
            @Nonnull LocalDate startDate,
            @Nonnull LocalDate endDate);
}
