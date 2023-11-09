// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

// Reference: https://fixer.io/documentation#supportedsymbols (last checked: 21-08-2023 15:28)
// Keep in mind that this provider does not explicitly document the nullability of the provided data.
@JsonIgnoreProperties({"success"})
record FixerCurrencySetDTO(Map<String, String> symbols) {} // Package-private
