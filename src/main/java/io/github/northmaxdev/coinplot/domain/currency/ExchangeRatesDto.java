// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain.currency;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExchangeRatesDto(String base, Map<LocalDate, Map<String, BigDecimal>> rates) {}
