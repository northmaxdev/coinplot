// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

@Embeddable
public class Exchange implements Serializable {

    // FIXME: Do we need to implement serialVersionUID? Do types of properties also need to be Serializable?
    // TODO: Play with FetchType configurations (eager vs lazy)

    @ManyToOne(optional = false)
    private @Nonnull Currency base;

    @ManyToOne(optional = false)
    private @Nonnull Currency target;

    @Column(nullable = false)
    private @Nonnull LocalDate date;

    public Exchange(@Nonnull Currency base, @Nonnull Currency target, @Nonnull LocalDate date) {
        this.base = base;
        this.target = target;
        this.date = date;
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
        return '[' + base.getCode() + " --> " + target.getCode() + ", " + date.format(ISO_LOCAL_DATE) + ']';
    }
}
