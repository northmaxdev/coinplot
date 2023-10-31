// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.Series;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.exchange.DatelessExchange;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateBatch;
import io.github.northmaxdev.coinplot.lang.Maps;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class ExchangeRateDynamicsChart extends Chart implements LocaleChangeObserver {

    public ExchangeRateDynamicsChart() {
        super(ChartType.LINE);
    }

    public void visualize(@Nonnull Set<ExchangeRateBatch> exchangeRateBatches) {
        Objects.requireNonNull(exchangeRateBatches);

        // In terms of type semantics and interface contracts, we don't really need a List here,
        // but it's what the Vaadin Charts API requires.
        List<Series> series = exchangeRateBatches.stream()
                .map(batch -> {
                    DatelessExchange exchange = batch.getExchange();
                    List<DataSeriesItem> seriesItems = Maps.mapToList(batch.getValueTimeline(), (date, value) -> {
                        // LocalDate::toEpochDay returns a primitive long, but DataSeriesItem deals with instances of java.lang.Number,
                        // which leads to redundant boxing. Now scale this for 1000+ items, and you got a potentially significant
                        // performance issue.
                        return new DataSeriesItem(date.toEpochDay(), value); // Date is X, value is Y
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
