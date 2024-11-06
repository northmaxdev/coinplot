// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain.currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toUnmodifiableSet;

@Service
public final class FrankfurterService implements ExchangeRatesService {

    private final RestClient restClient;

    @Autowired
    public FrankfurterService(FrankfurterConfig config) {
        this.restClient = RestClient.create(config.host());
    }

    @Override
    public Set<Currency> getSupportedCurrencies() {
        Map<String, String> response = restClient.get()
                .uri("/currencies")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });

        if (response == null) {
            throw new IllegalStateException("DTO is null");
        }

        return response.keySet()
                .stream()
                .map(Currency::getInstance)
                .collect(toUnmodifiableSet());
    }

    @Override
    public Map<CurrencyExchange, BigDecimal> getExchangeRates(CurrencyExchangeBatch exchangesOfInterest) {
        return Map.of();
    }
}
