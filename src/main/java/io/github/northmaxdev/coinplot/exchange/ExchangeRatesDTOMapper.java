// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.exchange;

import io.github.northmaxdev.coinplot.common.DTOMapper;
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
    public @Nonnull Collection<ExchangeRate> map(@Nonnull ExchangeRatesDTO dto) {
        // TODO:
        //  Consider reimplementing this in a more procedural way. Reasons:
        //  1. This is pretty much unreadable
        //  2. Might throw ISE mid-stream when doing look-up for target currencies, which is not great
        //  When implementing using a classic for-loop, we can run a separate thread for each date and
        //  push everything to a thread-safe list impl (is throwing ISE from a different thread OK though?)
        Currency base = getCurrencyOrThrowISE(dto.base());

        return dto.rates()
                .entrySet()
                .stream()
                .flatMap(dateToDatasetEntry -> {
                    LocalDate date = dateToDatasetEntry.getKey();
                    return dateToDatasetEntry.getValue()
                            .entrySet()
                            .stream()
                            .map(isoCodeToValueEntry -> {
                                Currency target = getCurrencyOrThrowISE(isoCodeToValueEntry.getKey());
                                return new ExchangeRate(base, target, date, isoCodeToValueEntry.getValue());
                            });
                })
                .toList();
    }

    private @Nonnull Currency getCurrencyOrThrowISE(@Nullable String code) {
        return currencyService.getCurrency(code)
                .orElseThrow(() -> new IllegalStateException("Failed to identify currency by code \"" + code + '\"'));
    }
}
