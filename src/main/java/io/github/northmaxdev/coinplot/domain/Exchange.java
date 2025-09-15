// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import java.util.Currency;
import java.util.Objects;

public record Exchange(Currency base, Currency target) {

    public Exchange {
        Objects.requireNonNull(base, "base must not be null");
        Objects.requireNonNull(target, "target must not be null");
    }

    @Override
    public String toString() {
        return base.getCurrencyCode() + '/' + target.getCurrencyCode();
    }
}
