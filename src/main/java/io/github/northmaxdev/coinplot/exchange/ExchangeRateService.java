// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.exchange;

import io.github.northmaxdev.coinplot.common.core.LocalDateRange;
import io.github.northmaxdev.coinplot.currency.Currency;
import jakarta.annotation.Nonnull;

import java.util.Collection;

public interface ExchangeRateService {

    Collection<ExchangeRate> getExchangeRatesBetweenDates(
            @Nonnull Currency base,
            @Nonnull Collection<Currency> targets,
            @Nonnull LocalDateRange dateRange);
}
