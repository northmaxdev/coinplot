// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

// Keep in mind that this provider does not explicitly document the nullability of the provided data
public record EverapiExchangeRateSetDTO(List<DataObject> data) {

    @JsonIgnoreProperties({"datetime"})
    public record DataObject(Map<String, ExchangeRateObject> currencies) {

        @JsonIgnoreProperties({"code"})
        public record ExchangeRateObject(BigDecimal value) {}
    }
}
