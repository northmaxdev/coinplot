// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import java.math.BigDecimal;
import java.util.Map;

public final class ExchangeRatesDataset {

    private final Map<DatedExchange, BigDecimal> data;

    public ExchangeRatesDataset(Map<DatedExchange, BigDecimal> data) {
        this.data = Map.copyOf(data);
    }

    public Map<DatedExchange, BigDecimal> getData() {
        return data;
    }

    public Map<DatedExchange, BigDecimal> getLatestRates() {
        throw new UnsupportedOperationException();
    }

    // TODO:
    //  Map<Exchange, BigDecimalSummaryStatistics> getStatistics()
    //  Some considerations:
    //  1. Use BigDecimalSummaryStatistics in Eclipse Collections
    //  2. Use DoubleSummaryStatistics - we lose the benefits of BigDecimal in monetary calculations,
    //     but avoid importing Eclipse Collection for a single class
}
