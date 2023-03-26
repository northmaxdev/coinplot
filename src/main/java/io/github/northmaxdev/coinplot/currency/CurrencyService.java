// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.currency;

import jakarta.annotation.Nullable;

import java.util.Optional;
import java.util.Set;

public interface CurrencyService {

    Set<Currency> getAvailableCurrencies();

    Optional<Currency> getCurrency(@Nullable String threeLetterISOCode);
}
