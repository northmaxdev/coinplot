// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencySetDTOMapper;
import jakarta.annotation.Nonnull;

import java.util.Objects;
import java.util.Set;

public final class FixerCurrencySetDTOMapper implements CurrencySetDTOMapper<FixerCurrencySetDTO> {

    @Override
    public @Nonnull Set<Currency> map(@Nonnull FixerCurrencySetDTO dto) {
        Objects.requireNonNull(dto);
        return Currency.createSet(dto.symbols());
    }
}
