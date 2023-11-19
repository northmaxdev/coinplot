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
import io.github.northmaxdev.coinplot.backend.core.exchange.DatelessExchange;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateBatch;
import io.github.northmaxdev.coinplot.frontend.common.LocalizedMultiVisualizer;
import io.github.northmaxdev.coinplot.lang.Maps;
import jakarta.annotation.Nonnull;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public final class ExchangeRateDynamicsChart
        extends Chart
        implements LocalizedMultiVisualizer<ExchangeRateBatch> {

    private static final List<Series> NO_SERIES = List.of();

    public ExchangeRateDynamicsChart() {
        super(ChartType.SPLINE);
        Configuration config = getConfiguration();

        XAxis xAxis = config.getxAxis();
        xAxis.setType(AxisType.DATETIME);

        YAxis yAxis = config.getyAxis();
        yAxis.setType(AxisType.LINEAR);
    }

    @Override
    public void visualize(@Nonnull ExchangeRateBatch batch) {
        Objects.requireNonNull(batch);
        Series series = serializeBatch(batch);

        Configuration config = getConfiguration();
        config.setSeries(series);
        drawChart(true); // See Configuration::setSeries(List<Series>) JavaDoc for info
    }

    @Override
    public void visualize(@Nonnull Collection<ExchangeRateBatch> batches) {
        Objects.requireNonNull(batches);
        // Configuration::setSeries requires List<Series> instead of a Collection<Series>
        List<Series> series = batches.stream()
                .map(ExchangeRateDynamicsChart::serializeBatch)
                .toList();

        Configuration config = getConfiguration();
        config.setSeries(series);
        drawChart(true); // See Configuration::setSeries(List<Series>) JavaDoc for info
    }

    @Override
    public void clear() {
        Configuration config = getConfiguration();
        config.setSeries(NO_SERIES);
        drawChart(true); // See Configuration::setSeries(List<Series>) JavaDoc for info
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

    // "Serialize" as in "arrange something in a series", not the usual computer science meaning
    private static @Nonnull Series serializeBatch(@Nonnull ExchangeRateBatch batch) {
        DatelessExchange exchange = batch.getExchange();
        List<DataSeriesItem> seriesItems = Maps.mapToList(batch.getRateTimeline(), (date, value) -> {
            // Vaadin formats these timestamps without L10N in mind
            // (or maybe it just uses the JVM locale?)
            Instant timestamp = date.atStartOfDay()
                    .toInstant(ZoneOffset.UTC);
            return new DataSeriesItem(timestamp, value); // x, y
        });

        Series series = new DataSeries(seriesItems);
        series.setName(exchange.getLabel());
        return series;
    }
}
