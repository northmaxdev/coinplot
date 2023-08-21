// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencySetDTOMapper;
import io.github.northmaxdev.coinplot.backend.core.web.response.DTOMappingException;
import io.github.northmaxdev.coinplot.lang.NullChecks;
import jakarta.annotation.Nonnull;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public final class FixerCurrencySetDTOMapper implements CurrencySetDTOMapper<FixerCurrencySetDTO> {

    @Override
    public Set<Currency> map(@Nonnull FixerCurrencySetDTO dto) throws DTOMappingException {
        return NullChecks.forDTO(dto)
                .symbols()
                .entrySet()
                .stream()
                .map(Currency::ofMapEntry)
                .collect(toSet());
    }
}
