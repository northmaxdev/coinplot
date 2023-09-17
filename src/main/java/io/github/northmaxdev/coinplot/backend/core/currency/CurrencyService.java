// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.currency;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Optional;
import java.util.Set;

public interface CurrencyService {

    @Nonnull Set<Currency> getAvailableCurrencies();

    Optional<Currency> getCurrency(@Nullable String code);
}
