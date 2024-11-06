// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain.currency;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;
import java.util.Set;

public interface ExchangeRatesService {

    Set<Currency> getSupportedCurrencies();

    Map<CurrencyExchange, BigDecimal> getExchangeRates(CurrencyExchangeBatch exchangesOfInterest);
}
