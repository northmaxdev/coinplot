// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.currency;

import io.github.northmaxdev.coinplot.backend.web.response.DTOMapper;

import java.util.Set;

@FunctionalInterface
public interface CurrenciesDTOMapper<D> extends DTOMapper<D, Set<Currency>> {
}
