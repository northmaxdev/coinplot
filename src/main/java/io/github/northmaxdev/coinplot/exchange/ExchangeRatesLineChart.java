// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.exchange;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.AxisType;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.Series;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.currency.Currency;
import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;

public final class ExchangeRatesLineChart extends Chart implements LocaleChangeObserver {

    private final XAxis xAxis;
    private final YAxis yAxis;

    public ExchangeRatesLineChart() {
        super(ChartType.LINE);

        this.xAxis = new XAxis();
        xAxis.setType(AxisType.DATETIME);
        xAxis.setTitle(getXAxisTitleTranslation());

        this.yAxis = new YAxis();
        yAxis.setTitle(getYAxisTitleTranslation());

        var config = getConfiguration();
        config.addxAxis(xAxis);
        config.addyAxis(yAxis);
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        xAxis.setTitle(getXAxisTitleTranslation());
        yAxis.setTitle(getYAxisTitleTranslation());
        drawChart();
    }

    public void reloadData(@Nonnull Collection<ExchangeRate> data, boolean redrawImmediately) {
        List<Series> newSeries = organizeData(data)
                .entrySet()
                .stream()
                .map(entry -> {
                    Pair<Currency, Currency> exchange = entry.getKey();
                    Currency base = exchange.getLeft();
                    Currency target = exchange.getRight();

                    String seriesName = base.name() + " | " + target.name();
                    List<DataSeriesItem> seriesItems = entry.getValue()
                            .stream()
                            .map(ExchangeRatesLineChart::convertRateToSeriesItem)
                            .toList();

                    Series series = new DataSeries(seriesItems);
                    series.setName(seriesName);
                    return series;
                })
                .toList();

        var config = getConfiguration();
        config.setSeries(newSeries);

        if (redrawImmediately) {
            drawChart();
        }
    }

    public void reloadData(@Nonnull Collection<ExchangeRate> data) {
        reloadData(data, true);
    }

    private static Map<Pair<Currency, Currency>, Set<ExchangeRate>> organizeData(
            @Nonnull Collection<ExchangeRate> data) {
        // Group the exchange rates by the unique combination of their base and target currencies (similar to
        // composite keys in SQL), while the grouped exchange rates themselves are sorted by date
        return data.stream()
                .collect(groupingBy(rate -> new ImmutablePair<>(rate.base(), rate.target()), toCollection(() -> {
                    Comparator<ExchangeRate> comparator = Comparator.comparing(ExchangeRate::date);
                    return new TreeSet<>(comparator);
                })));
    }

    private static DataSeriesItem convertRateToSeriesItem(@Nonnull ExchangeRate rate) {
        Instant exchangeDateAsInstant = rate.date()
                .atStartOfDay()
                .toInstant(ZoneOffset.UTC); // UTC is selected for this matter completely arbitrarily

        return new DataSeriesItem(exchangeDateAsInstant, rate.value());
    }

    //////////
    // i18n //
    //////////

    private String getXAxisTitleTranslation() {
        return getTranslation("chart.x.title");
    }

    private String getYAxisTitleTranslation() {
        return getTranslation("chart.y.title");
    }
}
