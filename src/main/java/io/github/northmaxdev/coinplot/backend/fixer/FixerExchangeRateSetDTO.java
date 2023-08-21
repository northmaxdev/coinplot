// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

// Reference: https://fixer.io/documentation#timeseries (last checked: 21-08-2023 16:06)
// Keep in mind that this provider does not explicitly document the nullability of the provided data.
@JsonIgnoreProperties({"success", "timeseries", "start_date", "end_date"})
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record FixerExchangeRateSetDTO(String base, Map<LocalDate, Map<String, BigDecimal>> rates) {}
