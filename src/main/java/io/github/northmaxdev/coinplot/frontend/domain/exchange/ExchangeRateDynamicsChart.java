// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.AxisType;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.Series;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.exchange.DatelessExchange;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateBatch;
import io.github.northmaxdev.coinplot.lang.Maps;
import jakarta.annotation.Nonnull;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public final class ExchangeRateDynamicsChart extends Chart implements LocaleChangeObserver {

    public ExchangeRateDynamicsChart() {
        super(ChartType.SPLINE);
        Configuration config = getConfiguration();

        XAxis xAxis = config.getxAxis();
        xAxis.setType(AxisType.DATETIME);

        YAxis yAxis = config.getyAxis();
        yAxis.setType(AxisType.LINEAR);
    }

    public void visualize(@Nonnull Set<ExchangeRateBatch> exchangeRateBatches) {
        Objects.requireNonNull(exchangeRateBatches);

        // In terms of type semantics and interface contracts, we don't really need a List here,
        // but it's what the Vaadin Charts API requires.
        List<Series> series = exchangeRateBatches.stream()
                .map(batch -> {
                    DatelessExchange exchange = batch.getExchange();
                    List<DataSeriesItem> seriesItems = Maps.mapToList(batch.getValueTimeline(), (date, value) -> {
                        // Vaadin formats these timestamps without L10N in mind
                        // (or maybe it uses the JVM locale?)
                        Instant timestamp = date.atStartOfDay()
                                .toInstant(ZoneOffset.UTC);
                        return new DataSeriesItem(timestamp, value); // (x, y)
                    });

                    Series s = new DataSeries(seriesItems);
                    s.setName(exchange.getLabel());
                    return s;
                })
                .toList();

        Configuration config = getConfiguration();
        config.setSeries(series);

        drawChart(true); // See Configuration::setSeries JavaDoc for info
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        Configuration config = getConfiguration();
        Locale newLocale = event.getLocale();

        XAxis xAxis = config.getxAxis();
        String xAxisTitle = getTranslation(newLocale, "exchange-rate-dynamics-chart.x-axis.title");
        xAxis.setTitle(xAxisTitle);

        YAxis yAxis = config.getyAxis();
        String yAxisTitle = getTranslation(newLocale, "exchange-rate-dynamics-chart.y-axis.title");
        yAxis.setTitle(yAxisTitle);

        drawChart();
    }
}
