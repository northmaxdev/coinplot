// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.exchange;

import io.github.northmaxdev.coinplot.common.web.DTOMapper;
import io.github.northmaxdev.coinplot.common.web.DTOMappingException;
import io.github.northmaxdev.coinplot.currency.Currency;
import io.github.northmaxdev.coinplot.currency.CurrencyService;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;

@Component
public final class ExchangeRatesDTOMapper implements DTOMapper<ExchangeRatesDTO, Collection<ExchangeRate>> {

    private final CurrencyService currencyService;

    @Autowired
    public ExchangeRatesDTOMapper(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public @Nonnull Collection<ExchangeRate> map(@Nonnull ExchangeRatesDTO dto) throws DTOMappingException {
        Currency base = getCurrencyOrThrow(dto.base());

        return dto.rates()
                .entrySet()
                .stream()
                .flatMap(dateToMapEntry -> {
                    LocalDate date = dateToMapEntry.getKey();
                    return dateToMapEntry.getValue()
                            .entrySet()
                            .stream()
                            .map(strToDoubleEntry -> {
                                // Fail-fast: fetch currency first, then construct. Throwing exceptions mid-stream is
                                // not optimal, but this is a truly exceptional case, so if it happens, it'll probably
                                // matter more than all the stack unwinding.
                                Currency target = getCurrencyOrThrow(strToDoubleEntry.getKey());
                                double rateValue = strToDoubleEntry.getValue();
                                return new ExchangeRate(base, target, date, rateValue);
                            });
                })
                .toList();
    }

    private @Nonnull Currency getCurrencyOrThrow(@Nullable String code) throws DTOMappingException {
        try {
            return currencyService.getCurrency(code)
                    .orElseThrow(() -> new DTOMappingException("Failed to identify currency by code \"" + code + '\"'));
        } catch (Exception e) {
            throw new DTOMappingException("Failed to retrieve currency by code \"" + code + '\"', e);
        }
    }
}
