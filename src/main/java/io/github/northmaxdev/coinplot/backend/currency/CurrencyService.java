// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.currency;

import io.github.northmaxdev.coinplot.backend.ResourceFetchException;
import jakarta.annotation.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public interface CurrencyService {

    Set<Currency> getAvailableCurrencies() throws ResourceFetchException;

    default Optional<Currency> getCurrency(@Nullable String code) throws ResourceFetchException {
        Set<Currency> availableCurrencies = getAvailableCurrencies();
        return availableCurrencies.stream()
                .filter(currency -> Objects.equals(currency.getCode(), code))
                .findFirst();
    }
}
