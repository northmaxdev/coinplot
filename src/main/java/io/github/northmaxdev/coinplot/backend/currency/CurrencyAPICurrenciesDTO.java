// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.currency;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Map;

public record CurrencyAPICurrenciesDTO(Map<String, CurrencyData> data) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record CurrencyData(String name, String symbolNative) {}
}
