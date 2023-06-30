// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.currency;

import io.github.northmaxdev.coinplot.backend.response.DTOMapper;
import io.github.northmaxdev.coinplot.backend.response.DTOMappingException;
import jakarta.annotation.Nonnull;

import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public final class FrankfurterCurrenciesDTOMapper implements DTOMapper<Map<String, String>, Set<Currency>> {

    @Override
    public @Nonnull Set<Currency> map(@Nonnull Map<String, String> dto) throws DTOMappingException {
        return dto.entrySet()
                .stream()
                .map(entry -> new Currency(entry.getKey(), entry.getValue()))
                .collect(toSet());
    }
}
