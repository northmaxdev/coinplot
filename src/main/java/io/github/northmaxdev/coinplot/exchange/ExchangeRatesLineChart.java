// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.exchange;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.Series;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import io.github.northmaxdev.coinplot.currency.Currency;
import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

public final class ExchangeRatesLineChart extends Chart {

    public ExchangeRatesLineChart() {
        super(ChartType.LINE);

        XAxis xAxis = new XAxis();
        YAxis yAxis = new YAxis();

        var config = getConfiguration();
        config.addxAxis(xAxis);
        config.addyAxis(yAxis);
    }

    public void reloadData(@Nonnull Collection<ExchangeRate> data, boolean redrawImmediately) {
        List<Series> newSeries = data.stream()
                // Group the exchange rates by the unique combination of their base and target currencies (similar to
                // composite keys in SQL), while the grouped exchange rates themselves are stored sorted by date
                .collect(groupingBy(rate -> new ImmutablePair<>(rate.base(), rate.target()), toCollection(() -> {
                    Comparator<ExchangeRate> comparator = Comparator.comparing(ExchangeRate::date);
                    return new TreeSet<>(comparator);
                })))
                .entrySet()
                .stream()
                .map(entry -> {
                    Pair<Currency, Currency> exchange = entry.getKey();
                    Currency base = exchange.getLeft();
                    Currency target = exchange.getRight();

                    String seriesName = base.name() + " to " + target.name(); // TODO: i18n
                    List<Number> seriesValues = entry.getValue()
                            .stream()
                            .map(ExchangeRate::value)
                            .collect(toList());

                    return new ListSeries(seriesName, seriesValues);
                })
                .collect(toList());

        var config = getConfiguration();
        config.setSeries(newSeries);

        if (redrawImmediately) {
            drawChart();
        }
    }

    public void reloadData(@Nonnull Collection<ExchangeRate> data) {
        reloadData(data, true);
    }
}
