// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import java.util.Currency;
import java.util.Set;

public interface ExchangeRatesService {

    Set<Currency> getSupportedCurrencies();

    ExchangeRatesDataset getExchangeRates(DatedExchangeZip exchangesOfInterest);
}
