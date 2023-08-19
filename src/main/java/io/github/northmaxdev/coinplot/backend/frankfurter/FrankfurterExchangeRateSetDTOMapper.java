// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import io.github.northmaxdev.coinplot.backend.core.exchange.AbstractExchangeRateSetDTOMapper;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRate;
import io.github.northmaxdev.coinplot.backend.core.web.response.DTOMappingException;
import jakarta.annotation.Nonnull;

import java.util.Objects;
import java.util.Set;

public final class FrankfurterExchangeRateSetDTOMapper extends AbstractExchangeRateSetDTOMapper<FrankfurterExchangeRateSetDTO> {

    public FrankfurterExchangeRateSetDTOMapper(@Nonnull CurrencyService currencyDataSource) {
        super(currencyDataSource);
    }

    @Override
    public @Nonnull Set<ExchangeRate> map(@Nonnull FrankfurterExchangeRateSetDTO dto) throws DTOMappingException {
        Objects.requireNonNull(dto, "dto is null");
        throw new UnsupportedOperationException(); // TODO: Implement with unit tests
    }
}
