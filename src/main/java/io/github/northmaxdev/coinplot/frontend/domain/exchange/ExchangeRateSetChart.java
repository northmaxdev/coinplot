// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.Series;
import io.github.northmaxdev.coinplot.backend.core.exchange.DatelessExchange;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateStatistics;
import io.github.northmaxdev.coinplot.lang.chrono.Instants;
import jakarta.annotation.Nonnull;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public final class ExchangeRateSetChart extends Chart {

    public ExchangeRateSetChart() {
        super(ChartType.LINE);
    }

    public void visualize(@Nonnull Collection<ExchangeRateStatistics> statistics) {
        // TODO (Performance): Consider stream parallelization
        List<Series> series = statistics.stream() // Implicit null-check
                .map(ExchangeRateSetChart::createSeries)
                .toList();

        Configuration config = getConfiguration();
        config.setSeries(series);
        drawChart(true); // FIXME (Implementation): Is this needed? The Configuration::setSeries JavaDoc effectively says "yes"
    }

    private static @Nonnull Series createSeries(@Nonnull ExchangeRateStatistics statisticsEntry) {
        Objects.requireNonNull(statisticsEntry);

        DatelessExchange exchange = statisticsEntry.getExchange();
        List<DataSeriesItem> seriesItems = statisticsEntry.getRateValueChronology()
                .entrySet()
                // TODO (Performance): Consider stream parallelization
                .stream() // FIXME (Implementation): Does this keep the sorted state?
                .map(entry -> {
                    Instant x = Instants.toInstant(entry::getKey);
                    Number y = entry.getValue();
                    return new DataSeriesItem(x, y);
                })
                .toList();

        DataSeries series = new DataSeries(seriesItems);
        series.setName(exchange.getDisplayName());
        return series;
    }
}
