// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.currency;

import jakarta.annotation.Nullable;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public interface CurrencyService {

    Collection<Currency> getAvailableCurrencies() throws Exception;

    default Optional<Currency> getCurrency(@Nullable String threeLetterISOCode) throws Exception {
        var availableCurrencies = getAvailableCurrencies();
        return availableCurrencies.stream()
                .filter(currency -> Objects.equals(currency.threeLetterISOCode(), threeLetterISOCode))
                .findFirst();
    }
}
