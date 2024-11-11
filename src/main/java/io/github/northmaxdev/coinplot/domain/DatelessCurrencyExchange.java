// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import java.util.Currency;
import java.util.Objects;

public record DatelessCurrencyExchange(Currency base, Currency target) {

    public DatelessCurrencyExchange(Currency base, Currency target) {
        this.base = Objects.requireNonNull(base, "base must not be null");
        this.target = Objects.requireNonNull(target, "target must not be null");
    }

    @Override
    public String toString() {
        return base.getCurrencyCode() + '→' + target.getCurrencyCode();
    }
}
