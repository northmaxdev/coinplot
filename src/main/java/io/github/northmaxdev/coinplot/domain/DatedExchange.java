// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Currency;

import static java.util.Objects.requireNonNull;

public record DatedExchange(Exchange exchange, LocalDate date) {

    // https://data.ecb.europa.eu/methodology/exchange-rates
    private static final LocalTime USUAL_ECB_PUBLICATION_TIME = LocalTime.of(16, 0);
    private static final ZoneId ECB_TIMEZONE = ZoneId.of("Europe/Berlin");

    public DatedExchange {
        requireNonNull(exchange, "exchange must not be null");
        requireNonNull(date, "date must not be null");
    }

    public DatedExchange(Currency base, Currency target, LocalDate date) {
        this(new Exchange(base, target), date);
    }

    // Syntactic sugar
    public Exchange withoutDate() {
        return exchange;
    }

    public Instant approximatePublicationTimestamp() {
        return date.atTime(USUAL_ECB_PUBLICATION_TIME)
                .atZone(ECB_TIMEZONE)
                .toInstant();
    }

    @Override
    public String toString() {
        return exchange.toString() + ' ' + date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
