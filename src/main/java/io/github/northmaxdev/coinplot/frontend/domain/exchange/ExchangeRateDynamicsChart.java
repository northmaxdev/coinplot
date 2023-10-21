// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.Series;
import io.github.northmaxdev.coinplot.backend.core.exchange.DatelessExchange;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRate;
import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toUnmodifiableSet;

public final class ExchangeRateDynamicsChart extends AbstractExchangeRateSetChart {

    public ExchangeRateDynamicsChart() {
        super(ChartType.LINE);
    }

    public boolean isSmooth() {
        ChartType chartType = getConfiguration()
                .getChart()
                .getType();

        if (chartType.equals(ChartType.SPLINE)) return true;
        if (chartType.equals(ChartType.LINE)) return false;
        throw new IllegalStateException("ChartType is neither SPLINE nor LINE");
    }

    public void smoothen(boolean value) {
        ChartType chartType = value ? ChartType.SPLINE : ChartType.LINE;

        getConfiguration()
                .getChart()
                .setType(chartType);

        drawChart();
    }

    @Override
    protected @Nonnull List<Series> createSeriesFromDataset(@Nonnull Set<ExchangeRate> dataset) {
        Map<DatelessExchange, Set<ExchangeRate>> groupedExchangeRates = dataset.stream()
                .collect(groupingBy(DatelessExchange::of, toUnmodifiableSet()));

        return groupedExchangeRates.entrySet()
                .stream()
                .map(entry -> {
                    DatelessExchange exchange = entry.getKey();
                    List<DataSeriesItem> seriesItems = entry.getValue()
                            .stream()
                            .sorted(ExchangeRate.CHRONOLOGICAL_ORDER)
                            .map(ExchangeRateDynamicsChart::createSeriesItem)
                            .toList();

                    Series series = new DataSeries(seriesItems);
                    series.setName(exchange.toString()); // Remember that toString() may not always be suitable for UI
                    return series;
                })
                .toList();
    }

    private static @Nonnull DataSeriesItem createSeriesItem(@Nonnull ExchangeRate er) {
        long x = er.getDate().toEpochDay();
        BigDecimal y = er.getValue();

        // LocalDate::toEpochDay returns a primitive long, but DataSeriesItem deals with instances of java.lang.Number,
        // which leads to redundant boxing. Now scale this for 1000+ items, and you got a potentially significant
        // performance issue.
        return new DataSeriesItem(x, y);
    }
}
