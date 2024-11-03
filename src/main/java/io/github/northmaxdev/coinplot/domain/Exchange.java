// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import io.github.northmaxdev.coinplot.util.Chars;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.Objects;

public record Exchange(Currency base, Currency target, LocalDate date) {

    public Exchange(Currency base, Currency target, LocalDate date) {
        this.base = Objects.requireNonNull(base, "base must not be null");
        this.target = Objects.requireNonNull(target, "target must not be null");
        this.date = Objects.requireNonNull(date, "date must not be null");
    }

    public DatelessExchange withoutDate() {
        // TODO: This can be cached
        return new DatelessExchange(base, target);
    }

    @Override
    public String toString() {
        return base.getCurrencyCode()
                + Chars.RIGHT_DIRECTION_ARROW
                + target.getCurrencyCode()
                + date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
