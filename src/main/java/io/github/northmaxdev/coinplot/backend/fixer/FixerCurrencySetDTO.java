// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FixerCurrencySetDTO(Map<String, String> symbols) {}
