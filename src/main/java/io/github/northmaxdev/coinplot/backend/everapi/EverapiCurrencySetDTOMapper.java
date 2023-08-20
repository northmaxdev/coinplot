// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencySetDTOMapper;
import io.github.northmaxdev.coinplot.backend.core.web.response.DTOMappingException;
import jakarta.annotation.Nonnull;

import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public final class EverapiCurrencySetDTOMapper implements CurrencySetDTOMapper<EverapiCurrencySetDTO> {

    @Override
    public Set<Currency> map(@Nonnull EverapiCurrencySetDTO dto) throws DTOMappingException {
        return Objects.requireNonNull(dto, "EverapiCurrencySetDTOMapper received null DTO")
                .data()
                .entrySet()
                .stream()
                .map(entry -> {
                    EverapiCurrencySetDTO.CurrencyData currencyData = entry.getValue();
                    return new Currency(entry.getKey(), currencyData.name(), currencyData.symbolNative());
                })
                .collect(toSet());
    }
}
