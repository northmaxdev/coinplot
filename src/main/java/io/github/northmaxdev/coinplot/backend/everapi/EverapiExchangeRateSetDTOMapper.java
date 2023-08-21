// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import io.github.northmaxdev.coinplot.backend.core.exchange.AbstractExchangeRateSetDTOMapper;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRate;
import io.github.northmaxdev.coinplot.backend.core.web.response.DTOMappingException;
import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public final class EverapiExchangeRateSetDTOMapper extends AbstractExchangeRateSetDTOMapper<EverapiExchangeRateSetDTO> {

    public EverapiExchangeRateSetDTOMapper(@Nonnull CurrencyService currencyDataSource) {
        super(currencyDataSource);
    }

    @Override
    public Set<ExchangeRate> map(@Nonnull EverapiExchangeRateSetDTO dto) throws DTOMappingException {
        return Objects.requireNonNull(dto, "EverapiExchangeRateSetDTOMapper received null DTO")
                .data()
                .stream()
                .flatMap(dataObject -> {
                    LocalDate date = dataObject.datetime()
                            .toLocalDate();
                    return dataObject.currencies()
                            .entrySet()
                            .stream()
                            .map(entry -> {
                                BigDecimal value = entry.getValue()
                                        .value();
                                // FIXME: This might lead to the project completely scrapping support for Everapi's CurrencyAPI :(
                                Currency base = getCurrencyOrThrowDME("The API does not fucking provide the base code in the response!");
                                Currency target = getCurrencyOrThrowDME(entry.getKey());
                                return new ExchangeRate(base, target, date, value);
                            });
                })
                .collect(toSet());
    }
}
