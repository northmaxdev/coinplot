// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.Objects;

public record CurrencyExchange(Currency base, Currency target, LocalDate date) {

    private static final LocalTime USUAL_ECB_PUBLICATION_TIME = LocalTime.of(16, 0);
    private static final ZoneOffset ECB_PUBLICATION_ZONE_OFFSET = ZoneOffset.ofHours(1); // CET

    public CurrencyExchange(Currency base, Currency target, LocalDate date) {
        this.base = Objects.requireNonNull(base, "base must not be null");
        this.target = Objects.requireNonNull(target, "target must not be null");
        this.date = Objects.requireNonNull(date, "date must not be null");
    }

    public DatelessCurrencyExchange withoutDate() {
        // This can be cached
        return new DatelessCurrencyExchange(base, target);
    }

    public Instant approximatePublicationTimestamp() {
        // This does not cover the case of CET/CEST transitions
        return date.atTime(USUAL_ECB_PUBLICATION_TIME)
                .toInstant(ECB_PUBLICATION_ZONE_OFFSET);
    }

    @Override
    public String toString() {
        return base.getCurrencyCode() + '→' + target.getCurrencyCode() + ' ' + date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
