// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APIConfig {

    @Value("${api.uri.currencies}")
    private String currenciesURI;

    @Value("${api.uri.exchange-rates.time-series.fmt}")
    private String exchangeRatesURIFormat;

    public String getCurrenciesURI() {
        return currenciesURI;
    }

    public String getExchangeRatesURIFormat() {
        return exchangeRatesURIFormat;
    }
}
