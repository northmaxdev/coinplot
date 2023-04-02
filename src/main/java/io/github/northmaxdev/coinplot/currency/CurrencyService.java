// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.currency;

import jakarta.annotation.Nullable;

import java.util.Collection;
import java.util.Optional;

public interface CurrencyService {

    Collection<Currency> getAvailableCurrencies() throws Exception;

    Optional<Currency> getCurrency(@Nullable String threeLetterISOCode) throws Exception;
}
