// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.currency;

import io.github.northmaxdev.coinplot.backend.core.web.response.DTOMapper;

import java.util.Set;

@FunctionalInterface
public interface CurrencySetDTOMapper<D> extends DTOMapper<D, Set<Currency>> {
}
