// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;
import java.util.Set;

public interface ExchangeRatesService {

    Set<Currency> getSupportedCurrencies();

    Map<DatedExchange, BigDecimal> getExchangeRates(CurrencyExchangeBatch exchangesOfInterest);
}
