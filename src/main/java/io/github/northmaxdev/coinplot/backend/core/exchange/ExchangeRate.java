// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class ExchangeRate implements Serializable { // Required by the JPA spec to be non-final (JPA 3.1, section 2.1)

    @Serial
    private static final long serialVersionUID = 7191575760397094585L;

    @EmbeddedId
    @Column(nullable = false)
    private @Nonnull Exchange exchange;

    // Using the name "value" leads to SQL execution errors.
    // This probably means that "value" is a reserved keyword.
    @Column(nullable = false)
    private @Nonnull BigDecimal rateValue;

    public ExchangeRate(
            @Nonnull Currency base,
            @Nonnull Currency target,
            @Nonnull LocalDate date,
            @Nonnull BigDecimal value) {
        exchange = new Exchange(base, target, date);
        this.rateValue = Objects.requireNonNull(value, "Exchange rate value cannot be null");
    }

    protected ExchangeRate() {
        // JPA spec requires a public or protected no-arg constructor (JPA 3.1, section 2.1)
    }

    public @Nonnull Exchange getExchange() {
        return exchange;
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
        return rateValue;
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
        return exchange + " " + rateValue;
    }
}
