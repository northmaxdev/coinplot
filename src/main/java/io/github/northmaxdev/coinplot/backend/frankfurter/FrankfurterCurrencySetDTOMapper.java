// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencySetDTOMapper;
import io.github.northmaxdev.coinplot.backend.core.web.response.DTOMappingException;
import jakarta.annotation.Nonnull;

import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public final class FrankfurterCurrencySetDTOMapper implements CurrencySetDTOMapper<Map<String, String>> {

    @Override
    public @Nonnull Set<Currency> map(@Nonnull Map<String, String> dto) throws DTOMappingException {
        return dto.entrySet()
                .stream()
                .map(Currency::ofMapEntry)
                .collect(toSet());
    }
}
