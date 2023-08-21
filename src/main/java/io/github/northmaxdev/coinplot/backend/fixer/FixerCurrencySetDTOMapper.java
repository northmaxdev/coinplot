// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencySetDTOMapper;
import io.github.northmaxdev.coinplot.backend.core.web.response.DTOMappingException;
import jakarta.annotation.Nonnull;

import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public final class FixerCurrencySetDTOMapper implements CurrencySetDTOMapper<FixerCurrencySetDTO> {

    @Override
    public @Nonnull Set<Currency> map(@Nonnull FixerCurrencySetDTO dto) throws DTOMappingException {
        return Objects.requireNonNull(dto)
                .symbols()
                .entrySet()
                .stream()
                .map(Currency::ofMapEntry)
                .collect(toSet());
    }
}
