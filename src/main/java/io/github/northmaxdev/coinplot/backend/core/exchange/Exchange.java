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
import java.util.Objects;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

@Embeddable
public class Exchange implements Serializable { // IDK if JPA requires an @Embeddable to be non-final, but just in case

    @Serial
    private static final long serialVersionUID = 4961628072462390664L;

    // TODO: Play with FetchType configurations (eager vs lazy)

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
        // JPA spec requires a public or protected no-arg constructor
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

    @Override
    public boolean equals(Object obj) {
        // Null-safety just in case
        return obj instanceof Exchange that
                && Objects.equals(this.base, that.base)
                && Objects.equals(this.target, that.target)
                && Objects.equals(this.date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, target, date);
    }

    @Override
    public String toString() {
        // Note: this might be problematic in terms of null-safety (see related note in Currency::toString)
        return '[' + base.getCode() + " --> " + target.getCode() + ", " + date.format(ISO_LOCAL_DATE) + ']';
    }
}
