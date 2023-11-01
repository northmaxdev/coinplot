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
import java.util.Objects;
import java.util.Set;

public final class ExchangeRateDynamicsChart extends Chart implements LocaleChangeObserver {

    // TODO (Self-note):
    //  My Vaadin Pro license expired around the end of October 2023.
    //  Due to this, I couldn't verify whether the latest changes I made to this class actually work as expected.
    //  Due to certain life circumstances, this will have to wait until things get sorted out.
    //  Below are the unverified changes:
    //  1. Axis type configuration and DataSeriesItem creation with a timestamp as the X value
    //  (in other words, ensure the axes look and function correctly)

    public ExchangeRateDynamicsChart() {
        super(ChartType.LINE);

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
                        Instant timestamp = date.atStartOfDay()
                                .toInstant(ZoneOffset.UTC);
                        return new DataSeriesItem(timestamp, value); // (x, y)
                    });

                    Series s = new DataSeries(seriesItems);
                    s.setName(exchange.toString()); // Remember that toString() may not always be suitable for UI
                    return s;
                })
                .toList();

        Configuration config = getConfiguration();
        config.setSeries(series);

        drawChart(true); // See Configuration::setSeries JavaDoc for info
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent localeChangeEvent) {
    }
}
