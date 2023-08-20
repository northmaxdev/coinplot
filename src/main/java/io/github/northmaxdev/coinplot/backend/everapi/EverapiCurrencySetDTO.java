// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Map;

// Reference: https://currencyapi.com/docs/currencies (last checked: 20-08-2023 18:00)
// Keep in mind that this provider does not explicitly document the nullability of the provided data.
public record EverapiCurrencySetDTO(Map<String, CurrencyObject> data) {

    @JsonIgnoreProperties({"symbol", "decimal_digits", "rounding", "code", "name_plural"})
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record CurrencyObject(String name, String symbolNative) {}
}
