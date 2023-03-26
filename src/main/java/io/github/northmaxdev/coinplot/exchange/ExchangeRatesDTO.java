// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.exchange;

import java.time.LocalDate;
import java.util.Map;

public record ExchangeRatesDTO(String base, Map<LocalDate, Map<String, Double>> rates) {}
