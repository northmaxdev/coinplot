// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.web.response.DTOMapper;

import java.util.Set;

@FunctionalInterface
public interface ExchangeRateSetDTOMapper<D> extends DTOMapper<D, Set<ExchangeRate>> {
}
