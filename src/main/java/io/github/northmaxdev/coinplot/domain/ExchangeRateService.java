// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import io.github.northmaxdev.coinplot.langext.LocalDateInterval;

import java.util.Currency;
import java.util.Map;
import java.util.Set;

public interface ExchangeRateService {

    Set<Currency> getSupportedCurrencies();

    Map<CurrencyPair, ExchangeRate> getLatestExchangeRates(Currency base);

    Set<ExchangeRate> getHistoricalExchangeRates(CurrencyPair currencyPair, LocalDateInterval dateInterval);
}
