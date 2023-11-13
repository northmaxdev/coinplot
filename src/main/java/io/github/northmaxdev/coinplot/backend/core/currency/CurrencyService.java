// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.currency;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Optional;
import java.util.Set;

public interface CurrencyService {

    CurrencyService NO_OP = new CurrencyService() {

        @Override
        public @Nonnull Set<Currency> getAvailableCurrencies() {
            return Set.of();
        }

        @Override
        public Optional<Currency> getCurrency(@Nullable String code) {
            return Optional.empty();
        }
    };

    @Nonnull Set<Currency> getAvailableCurrencies();

    Optional<Currency> getCurrency(@Nullable String code);
}
