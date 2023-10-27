// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.RangeSeries;
import com.vaadin.flow.component.charts.model.Series;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateBatch;
import jakarta.annotation.Nonnull;

public final class ExchangeRateExtremesChart extends AbstractExchangeRateDataChart {

    public ExchangeRateExtremesChart() {
        super(ChartType.COLUMNRANGE);
    }

    @Override
    protected @Nonnull Series mapBatchToSeriesIgnoringName(@Nonnull ExchangeRateBatch batch) {
        return batch.getValueExtremes()
                .map(extremes -> {
                    Number[] lowHighPair = {extremes.first(), extremes.second()};
                    return new RangeSeries(lowHighPair);
                })
                .orElseGet(RangeSeries::new);
    }

    @Override
    protected @Nonnull String getTitleI18NKey() {
        return "exchange-rate-extremes-chart.title";
    }
}
