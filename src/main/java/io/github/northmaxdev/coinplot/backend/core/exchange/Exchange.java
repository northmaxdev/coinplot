// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Embeddable
public class Exchange implements Serializable { // Required by the JPA spec to be non-final (JPA 3.1, section 2.6)

    @Serial
    private static final long serialVersionUID = 4961628072462390664L;

    // TODO (Performance): Play with FetchType configurations (eager vs lazy)

    @ManyToOne(optional = false)
    private @Nonnull Currency base;

    @ManyToOne(optional = false)
    private @Nonnull Currency target;

    @Column(nullable = false)
    private @Nonnull LocalDate date;

    public Exchange(@Nonnull Currency base, @Nonnull Currency target, @Nonnull LocalDate date) {
        this.base = Objects.requireNonNull(base, "Exchange base currency cannot be null");
        this.target = Objects.requireNonNull(target, "Exchange target currency cannot be null");
        this.date = Objects.requireNonNull(date, "Exchange date cannot be null");
    }

    protected Exchange() {
        // JPA spec requires a public or protected no-arg constructor (JPA 3.1, section 2.1)
    }

    public @Nonnull Currency getBase() {
        return base;
    }

    public @Nonnull Currency getTarget() {
        return target;
    }

    public @Nonnull LocalDate getDate() {
        return date;
    }

    public @Nonnull DatelessExchange withoutDate() {
        // TODO (Performance): This may be lazy-computed and cached
        // DatelessExchange::of(Exchange) itself may or may not use Exchange::withoutDate as its implementation,
        // so we use the canonical constructor to avoid funny stack overflows.
        return new DatelessExchange(base, target);
    }

    @Override
    public boolean equals(Object obj) {
        // https://jqno.nl/equalsverifier/errormessages/jpa-direct-reference-instead-of-getter/
        return obj instanceof Exchange that
                && Objects.equals(this.getBase(), that.getBase())
                && Objects.equals(this.getTarget(), that.getTarget())
                && Objects.equals(this.date, that.date);
    }

    @Override
    public int hashCode() {
        // https://jqno.nl/equalsverifier/errormessages/jpa-direct-reference-instead-of-getter/
        return Objects.hash(getBase(), getTarget(), date);
    }

    @Override
    public String toString() {
        DatelessExchange datelessExchange = withoutDate();
        return datelessExchange + " " + date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
