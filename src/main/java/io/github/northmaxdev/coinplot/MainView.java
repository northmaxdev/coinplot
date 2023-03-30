// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import io.github.northmaxdev.coinplot.currency.CurrencyService;
import io.github.northmaxdev.coinplot.exchange.ExchangeRate;
import io.github.northmaxdev.coinplot.exchange.ExchangeRateService;
import io.github.northmaxdev.coinplot.exchange.ExchangeRatesLineChart;
import io.github.northmaxdev.coinplot.exchange.ExchangeRatesRequestForm;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Route
public final class MainView extends HorizontalLayout {

    @Autowired
    public MainView(CurrencyService currencyService, ExchangeRateService exchangeRateService) {
        var chart = new ExchangeRatesLineChart();
        var form = new ExchangeRatesRequestForm(currencyService, (base, targets, dateRange) -> {
            Collection<ExchangeRate> data = exchangeRateService.getExchangeRatesBetweenDates(base, targets, dateRange);
            chart.reloadData(data, true);
        });

        add(chart, form);
        setPadding(true);
    }
}
