// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.Series;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateBatch;
import jakarta.annotation.Nonnull;

public final class ExchangeRateLatestChangeChart extends AbstractExchangeRateDataChart {

    public ExchangeRateLatestChangeChart() {
        super(ChartType.COLUMN);
    }

    @Override
    protected @Nonnull Series mapBatchToSeriesIgnoringName(@Nonnull ExchangeRateBatch batch) {
        return batch.getLatestChangePercentage()
                .map(percentage -> new ListSeries(percentage.value()))
                .orElseGet(ListSeries::new);
    }

    @Override
    protected @Nonnull String getTitleI18NKey() {
        return "exchange-rate-latest-change-chart.title";
    }
}
