// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import java.util.Currency;

import static java.util.Objects.requireNonNull;

public record Exchange(Currency base, Currency target) {

    public Exchange {
        requireNonNull(base, "base must not be null");
        requireNonNull(target, "target must not be null");
    }

    @Override
    public String toString() {
        return base.getCurrencyCode() + '/' + target.getCurrencyCode();
    }
}
