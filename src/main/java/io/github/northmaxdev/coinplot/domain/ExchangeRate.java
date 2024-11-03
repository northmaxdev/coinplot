// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import java.math.BigDecimal;
import java.util.Objects;

public record ExchangeRate(Exchange exchange, BigDecimal value) {

    public ExchangeRate(Exchange exchange, BigDecimal value) {
        this.exchange = Objects.requireNonNull(exchange, "exchange must not be null");
        this.value = Objects.requireNonNull(value, "value must not be null");
    }

    public Exchange getExchangeWithoutRate() {
        return exchange;
    }

    @Override
    public String toString() {
        return exchange + " [" + value + ']';
    }
}
