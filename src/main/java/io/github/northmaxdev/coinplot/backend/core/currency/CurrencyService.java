// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.currency;

import io.github.northmaxdev.coinplot.backend.core.FailedDataFetchException;
import jakarta.annotation.Nullable;

import java.util.Optional;
import java.util.Set;

public interface CurrencyService {

    Set<Currency> getAvailableCurrencies() throws FailedDataFetchException;

    Optional<Currency> getCurrency(@Nullable String code) throws FailedDataFetchException;
}
