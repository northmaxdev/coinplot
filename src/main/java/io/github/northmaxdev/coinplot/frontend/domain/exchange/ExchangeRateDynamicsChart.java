// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.Series;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateBatch;
import io.github.northmaxdev.coinplot.lang.Maps;
import jakarta.annotation.Nonnull;

import java.util.List;

public final class ExchangeRateDynamicsChart extends AbstractExchangeRateDataChart {

    public ExchangeRateDynamicsChart() {
        super(ChartType.LINE);
    }

    @Override
    protected @Nonnull Series mapBatchToSeriesIgnoringName(@Nonnull ExchangeRateBatch batch) {
        List<DataSeriesItem> seriesItems = Maps.mapToList(batch.getValueTimeline(), (date, value) -> {
            // LocalDate::toEpochDay returns a primitive long, but DataSeriesItem deals with instances of java.lang.Number,
            // which leads to redundant boxing. Now scale this for 1000+ items, and you got a potentially significant
            // performance issue.
            return new DataSeriesItem(date.toEpochDay(), value); // Date is X, value is Y
        });

        return new DataSeries(seriesItems);
    }
}
