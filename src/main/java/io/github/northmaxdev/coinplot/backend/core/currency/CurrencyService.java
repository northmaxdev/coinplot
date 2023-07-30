// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.currency;

import io.github.northmaxdev.coinplot.backend.core.ResourceFetchFailureException;
import jakarta.annotation.Nullable;

import java.util.Optional;
import java.util.Set;

public interface CurrencyService {

    Set<Currency> getAvailableCurrencies() throws ResourceFetchFailureException;

    Optional<Currency> getCurrency(@Nullable String code) throws ResourceFetchFailureException;
}
