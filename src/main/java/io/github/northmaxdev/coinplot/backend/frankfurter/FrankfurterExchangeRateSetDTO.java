// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

// Reference: https://www.frankfurter.app/docs/#timeseries (last checked: 22-08-2023 12:59)
// Keep in mind that this provider does not explicitly document the nullability of the provided data.
@JsonIgnoreProperties({"amount", "start_date", "end_date"})
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record FrankfurterExchangeRateSetDTO(String base, Map<LocalDate, Map<String, BigDecimal>> rates) {}
