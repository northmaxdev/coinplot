// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.ui.panels;

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
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toUnmodifiableList;


public final class ExchangeRatesPlot extends Chart {

    private static final List<Series> NO_SERIES = List.of();

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
        // Input:
        // [
        //     {PLN, HUF, 01-11-2024, 93.51}
        //     {EUR, SEK, 27-10-2024, 11.46}
        //     {EUR, SEK, 18-10-2024, 11.43}
        //     {EUR, JPY, 12-11-2024, 163.71}
        //     {EUR, SEK, 16-10-2024, 11.41}
        //     {EUR, JPY, 04-11-2024, 165.47}
        //     {EUR, AUD, 01-11-2024, 1.66}
        //     {EUR, AUD, 27-10-2024, 1.63}
        //     {EUR, SEK, 21-10-2024, 11.42}
        //     {EUR, AUD, 03-11-2024, 1.65}
        //     {EUR, JPY, 05-11-2024, 165.93}
        //     {PLN, HUF, 28-10-2024, 92.91}
        //     {EUR, JPY, 01-11-2024, 165.54}
        //     {PLN, HUF, 25-10-2024, 93.11}
        //     {PLN, HUF, 23-10-2024, 92.66}
        //     {EUR, AUD, 09-11-2024, 1.63}
        // ]
        //
        // Expected output:
        // [
        //     {EUR, JPY, 01-11-2024, 165.54}
        //     {EUR, JPY, 04-11-2024, 165.47}
        //     {EUR, JPY, 05-11-2024, 165.93}
        //     {EUR, JPY, 12-11-2024, 163.71}
        //
        //     {PLN, HUF, 23-10-2024, 92.66}
        //     {PLN, HUF, 25-10-2024, 93.11}
        //     {PLN, HUF, 28-10-2024, 92.91}
        //     {PLN, HUF, 01-11-2024, 93.51}
        //
        //     {EUR, SEK, 16-10-2024, 11.41}
        //     {EUR, SEK, 18-10-2024, 11.43}
        //     {EUR, SEK, 21-10-2024, 11.42}
        //     {EUR, SEK, 27-10-2024, 11.46}
        //
        //     {EUR, AUD, 27-10-2024, 1.63}
        //     {EUR, AUD, 01-11-2024, 1.66}
        //     {EUR, AUD, 03-11-2024, 1.65}
        //     {EUR, AUD, 09-11-2024, 1.63}
        // ]
        Map<DatelessCurrencyExchange, List<DataSeriesItem>> pointsGroupedByExchange = exchangeRates.entrySet()
                .stream()
                .collect(groupingBy(mapEntry -> {
                    CurrencyExchange exchange = mapEntry.getKey();
                    return exchange.withoutDate();
                }, mapping(mapEntry -> createPoint(mapEntry.getKey(), mapEntry.getValue()), toUnmodifiableList())));

        List<Series> series = pointsGroupedByExchange.entrySet()
                .stream()
                .map(mapEntry -> createSeries(mapEntry.getKey(), mapEntry.getValue()))
                .toList();

        redraw(series);
    }

    public void clear() {
        redraw(NO_SERIES);
    }

    //------------------//
    // Instance helpers //
    //------------------//

    private void redraw(List<Series> series) {
        Configuration config = getConfiguration();
        config.setSeries(series);
        drawChart(true); // See Configuration::setSeries(List<Series>) JavaDoc for info
    }

    //----------------//
    // Static helpers //
    //----------------//

    private static DataSeriesItem createPoint(CurrencyExchange exchange, BigDecimal rate) {
        DataSeriesItem plotPoint = new DataSeriesItem();

        plotPoint.setX(exchange.approximatePublicationTimestamp());
        plotPoint.setY(rate);
        // TODO: Play around with additional configuration, such as setDescription(), setCursor(), setName(), etc.

        return plotPoint;
    }

    private static Series createSeries(DatelessCurrencyExchange exchange, List<DataSeriesItem> points) {
        Series series = new DataSeries(points);
        series.setName(exchange.toString()); // TODO: Use something proper
        // TODO: Play around with additional series configuration
        return series;
    }
}
