// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import jakarta.annotation.Nonnull;

import java.util.Objects;
import java.util.Set;

public final class CommonExchangeRateSetDTOMapper extends AbstractExchangeRateSetDTOMapper<CommonExchangeRateSetDTO> {

    public CommonExchangeRateSetDTOMapper(@Nonnull CurrencyService currencyDataSource) {
        super(currencyDataSource);
    }

    @Override
    public @Nonnull Set<ExchangeRate> map(@Nonnull CommonExchangeRateSetDTO dto) {
        Objects.requireNonNull(dto);

        return Set.of(); // TODO: Implement
    }
}
