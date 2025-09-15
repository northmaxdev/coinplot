// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.Objects;

public record CurrencyExchange(Currency base, Currency target, LocalDate date) {

    // https://data.ecb.europa.eu/methodology/exchange-rates
    private static final LocalTime USUAL_ECB_PUBLICATION_TIME = LocalTime.of(16, 0);
    private static final ZoneId ECB_TIMEZONE = ZoneId.of("Europe/Berlin");

    public CurrencyExchange {
        Objects.requireNonNull(base, "base must not be null");
        Objects.requireNonNull(target, "target must not be null");
        Objects.requireNonNull(date, "date must not be null");
    }

    public Exchange withoutDate() {
        return new Exchange(base, target);
    }

    public Instant approximatePublicationTimestamp() {
        return date.atTime(USUAL_ECB_PUBLICATION_TIME)
                .atZone(ECB_TIMEZONE)
                .toInstant();
    }

    @Override
    public String toString() {
        return base.getCurrencyCode() + '/' + target.getCurrencyCode() + ' ' + date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
