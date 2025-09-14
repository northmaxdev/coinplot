package io.github.northmaxdev.coinplot.ui;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.AxisType;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.Series;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import io.github.northmaxdev.coinplot.domain.CurrencyExchange;
import io.github.northmaxdev.coinplot.domain.DatelessCurrencyExchange;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

public final class ExchangeRatesPlot extends Chart {

    private static final List<Series> EMPTY_LIST_OF_SERIES = List.of();

    // A comparator to sort {x,y} coordinates chronologically.
    // Since x is always a UNIX timestamp, we can always safely treat it as a regular in64 (long).
    private static final Comparator<DataSeriesItem> COORDINATE_CHRONOLOGICAL_ORDER = comparingLong(coordinate -> {
        Number x = coordinate.getX();
        return x.longValue();
    });

    public ExchangeRatesPlot() {
        super(ChartType.LINE);
        Configuration config = getConfiguration();

        // TODO: Play around with more axis configuration

        XAxis xAxis = config.getxAxis();
        xAxis.setType(AxisType.DATETIME);

        YAxis yAxis = config.getyAxis();
        yAxis.setType(AxisType.LINEAR);
    }

    public void plot(Map<CurrencyExchange, BigDecimal> exchangeRates) {
        // "DataSeriesItem" here is just a 2D coordinate on a line chart
        Map<DatelessCurrencyExchange, List<DataSeriesItem>> coordinatesGroupedByExchange = exchangeRates.entrySet()
                .stream()
                .collect(groupingBy(mapEntry -> {
                    CurrencyExchange exchange = mapEntry.getKey();
                    return exchange.withoutDate();
                }, mapping(mapEntry -> {
                    CurrencyExchange exchange = mapEntry.getKey();
                    BigDecimal rate = mapEntry.getValue();
                    // TODO: Make each coordinate display a tooltip with its value and date (see setName or setDescription)
                    //  date (x) is to be formatted in a more human-readable format, e.g. "January 1st, 2015"
                    //  rate (y) is to be formatted as a plain number - no exponents and mathematical notations (see BigDecimal::toPlainString)
                    return new DataSeriesItem(exchange.approximatePublicationTimestamp(), rate);
                }, toList()))); // List must be mutable because we're going to be sorting it in-place next

        List<Series> series = coordinatesGroupedByExchange.entrySet()
                .stream()
                .map(mapEntry -> {
                    DatelessCurrencyExchange exchange = mapEntry.getKey();
                    List<DataSeriesItem> coordinates = mapEntry.getValue();
                    // The charting library won't chronologically sort the data points for us - we must do so ourselves
                    // https://assets.highcharts.com/errors/15/
                    coordinates.sort(COORDINATE_CHRONOLOGICAL_ORDER);

                    Series singularSeries = new DataSeries(coordinates); // TODO: Rename
                    singularSeries.setName(exchange.toString()); // TODO: Use something better
                    return singularSeries;
                })
                .toList();

        redraw(series);
    }

    public void clear() {
        redraw(EMPTY_LIST_OF_SERIES);
    }

    private void redraw(List<Series> series) {
        Configuration config = getConfiguration();
        config.setSeries(series);
        drawChart(true); // See Configuration::setSeries(List<Series>) JavaDoc for info
    }
}
