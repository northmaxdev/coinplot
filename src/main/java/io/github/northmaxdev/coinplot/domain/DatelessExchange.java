// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import io.github.northmaxdev.coinplot.util.Chars;

import java.util.Currency;
import java.util.Objects;

public record DatelessExchange(Currency base, Currency target) {

    public DatelessExchange(Currency base, Currency target) {
        this.base = Objects.requireNonNull(base, "base must not be null");
        this.target = Objects.requireNonNull(target, "target must not be null");
    }

    public static DatelessExchange of(Exchange exchange) {
        // This is better to call than the constructor because it might cache
        return exchange.withoutDate();
    }

    public static DatelessExchange of(ExchangeRate exchangeRate) {
        return of(exchangeRate.getExchangeWithoutRate());
    }

    @Override
    public String toString() {
        return base.getCurrencyCode() + Chars.RIGHT_DIRECTION_ARROW + target.getCurrencyCode();
    }
}
