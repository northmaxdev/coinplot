// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import jakarta.annotation.Nonnull;

import java.util.Objects;

// This class exists primarily for cases when we absolutely need to represent a currency exchange without a date.
// One such scenario has to do with equals/hashCode semantics.
// This class has been retrofitted into the Exchange/ExchangeRate class structure with no intentions of making it JPA-related.
public record DatelessExchange(@Nonnull Currency base, @Nonnull Currency target) {

    public DatelessExchange {
        Objects.requireNonNull(base);
        Objects.requireNonNull(target);
    }

    public static @Nonnull DatelessExchange of(@Nonnull Exchange exchange) {
        return exchange.withoutDate();
    }

    public static @Nonnull DatelessExchange of(@Nonnull ExchangeRate exchangeRate) {
        return of(exchangeRate.getExchange());
    }
}
