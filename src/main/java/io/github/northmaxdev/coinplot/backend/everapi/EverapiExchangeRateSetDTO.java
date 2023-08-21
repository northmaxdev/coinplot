// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

// Reference: https://currencyapi.com/docs/range (last checked: 21-08-2023 14:12)
// Keep in mind that this provider does not explicitly document the nullability of the provided data.
// FIXME: It is unclear from the documentation above whether this DTO uses snake_case like the currency one
public record EverapiExchangeRateSetDTO(List<DataObject> data) {

    public record DataObject(LocalDateTime datetime, Map<String, ExchangeRateObject> currencies) {

        @JsonIgnoreProperties({"code"})
        public record ExchangeRateObject(BigDecimal value) {}
    }
}
