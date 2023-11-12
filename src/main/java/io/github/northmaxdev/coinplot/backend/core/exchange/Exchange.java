// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Objects;

@Embeddable
public class Exchange implements Serializable { // Required by the JPA spec to be non-final (JPA 3.1, section 2.6)

    public static final Comparator<Exchange> CHRONOLOGICAL_ORDER = Comparator.comparing(Exchange::getDate);

    @Serial
    private static final long serialVersionUID = 4961628072462390664L;

    @ManyToOne(optional = false)
    private @Nonnull Currency base;

    @ManyToOne(optional = false)
    private @Nonnull Currency target;

    // Using the name "date" leads to SQL execution errors.
    // This probably means that "date" is a reserved keyword.
    @Column(nullable = false)
    private @Nonnull LocalDate exchangeDate;

    public Exchange(@Nonnull Currency base, @Nonnull Currency target, @Nonnull LocalDate date) {
        this.base = Objects.requireNonNull(base, "Exchange base currency cannot be null");
        this.target = Objects.requireNonNull(target, "Exchange target currency cannot be null");
        this.exchangeDate = Objects.requireNonNull(date, "Exchange date cannot be null");
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
        return exchangeDate;
    }

    public @Nonnull DatelessExchange withoutDate() {
        // https://jqno.nl/equalsverifier/errormessages/jpa-direct-reference-instead-of-getter/
        return new DatelessExchange(getBase(), getTarget());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        // https://jqno.nl/equalsverifier/errormessages/jpa-direct-reference-instead-of-getter/
        return obj instanceof Exchange that
                && Objects.equals(this.getBase(), that.getBase())
                && Objects.equals(this.getTarget(), that.getTarget())
                && Objects.equals(this.exchangeDate, that.exchangeDate);
    }

    @Override
    public int hashCode() {
        // https://jqno.nl/equalsverifier/errormessages/jpa-direct-reference-instead-of-getter/
        return Objects.hash(getBase(), getTarget(), exchangeDate);
    }

    @Override
    public @Nonnull String toString() {
        return withoutDate() + " " + exchangeDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
