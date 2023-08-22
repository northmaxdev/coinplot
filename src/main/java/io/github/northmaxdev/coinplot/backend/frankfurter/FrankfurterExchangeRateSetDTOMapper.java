// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import io.github.northmaxdev.coinplot.backend.core.exchange.AbstractExchangeRateSetDTOMapper;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRate;
import io.github.northmaxdev.coinplot.backend.core.web.response.DTOMappingException;
import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public final class FrankfurterExchangeRateSetDTOMapper extends AbstractExchangeRateSetDTOMapper<FrankfurterExchangeRateSetDTO> {

    public FrankfurterExchangeRateSetDTOMapper(@Nonnull CurrencyService currencyDataSource) {
        super(currencyDataSource);
    }

    @Override
    public @Nonnull Set<ExchangeRate> map(@Nonnull FrankfurterExchangeRateSetDTO dto) throws DTOMappingException {
        Objects.requireNonNull(dto);

        Currency base = tryGetCurrency(dto.base());
        return dto.rates()
                .entrySet()
                .stream()
                .flatMap(entry -> flatten(entry.getValue(), base, entry.getKey()))
                .collect(toSet());
    }

    // No need to annotate nullability for parameters of a private helper method
    private Stream<ExchangeRate> flatten(Map<String, BigDecimal> m, Currency base, LocalDate date) throws DTOMappingException {
        return m.entrySet()
                .stream()
                .map(entry -> {
                    Currency target = tryGetCurrency(entry.getKey());
                    BigDecimal value = entry.getValue();
                    return new ExchangeRate(base, target, date, value);
                });
    }
}
