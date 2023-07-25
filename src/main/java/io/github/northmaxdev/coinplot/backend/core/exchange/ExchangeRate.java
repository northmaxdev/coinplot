// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class ExchangeRate {

    // FIXME: Serializable?

    @EmbeddedId
    @Column(nullable = false)
    private @Nonnull Exchange exchange;

    @Column(nullable = false)
    private @Nonnull BigDecimal value;

    public ExchangeRate(
            @Nonnull Currency base,
            @Nonnull Currency target,
            @Nonnull LocalDate date,
            @Nonnull BigDecimal value) {
        this.exchange = new Exchange(base, target, date);
        this.value = value;
    }

    protected ExchangeRate() {
        // JPA spec requires a public or protected no-arg constructor
    }

    public @Nonnull Currency getBase() {
        return exchange.getBase();
    }

    public @Nonnull Currency getTarget() {
        return exchange.getTarget();
    }

    public @Nonnull LocalDate getDate() {
        return exchange.getDate();
    }

    public @Nonnull BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ExchangeRate that
                && Objects.equals(this.exchange, that.exchange);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(exchange);
    }

    @Override
    public String toString() {
        return exchange + " (" + value + ')';
    }
}
