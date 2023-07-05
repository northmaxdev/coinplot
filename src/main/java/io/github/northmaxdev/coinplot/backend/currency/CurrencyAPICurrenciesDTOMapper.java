// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.currency;

import io.github.northmaxdev.coinplot.backend.web.response.DTOMappingException;
import jakarta.annotation.Nonnull;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public final class CurrencyAPICurrenciesDTOMapper implements CurrenciesDTOMapper<CurrencyAPICurrenciesDTO> {

    @Override
    public @Nonnull Set<Currency> map(@Nonnull CurrencyAPICurrenciesDTO dto) throws DTOMappingException {
        return dto.data()
                .entrySet()
                .stream()
                .map(entry -> {
                    CurrencyAPICurrenciesDTO.CurrencyData data = entry.getValue();
                    return new Currency(entry.getKey(), data.name(), data.symbolNative());
                })
                .collect(toSet());
    }
}
