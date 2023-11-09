// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

// Common DTO for many web APIs. Some of them document this as "money.js compatibility".
// See: https://openexchangerates.github.io/money.js/
@JsonIgnoreProperties(ignoreUnknown = true)
public record CommonExchangeRateSetDTO(@Nonnull String base, @Nonnull Map<LocalDate, Map<String, BigDecimal>> rates) {}
