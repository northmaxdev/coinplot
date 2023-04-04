// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.router.Route;
import io.github.northmaxdev.coinplot.common.ui.LocaleSelect;
import io.github.northmaxdev.coinplot.currency.CurrencyFetchFailureNotification;
import io.github.northmaxdev.coinplot.currency.CurrencyService;
import io.github.northmaxdev.coinplot.exchange.ExchangeRateFetchFailureNotification;
import io.github.northmaxdev.coinplot.exchange.ExchangeRateService;
import io.github.northmaxdev.coinplot.exchange.ExchangeRatesLineChart;
import io.github.northmaxdev.coinplot.exchange.ExchangeRatesRequestForm;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

@Route
public final class MainView extends AppLayout {

    @Autowired
    public MainView(
            CurrencyService currencyService,
            ExchangeRateService exchangeRateService,
            I18NProvider i18nProvider) {

        ///////////////////
        // Notifications //
        ///////////////////

        var noCurrenciesNotification = new CurrencyFetchFailureNotification();
        var noExchangeRatesNotification = new ExchangeRateFetchFailureNotification();

        /////////////
        // Primary //
        /////////////

        var chart = new ExchangeRatesLineChart();
        var primaryContent = new VerticalLayout(chart);
        setContent(primaryContent);

        ////////////
        // Drawer //
        ////////////

        var form = new ExchangeRatesRequestForm((base, targets, dateRange) -> {
            try {
                var exchangeRates = exchangeRateService.getExchangeRatesBetweenDates(base, targets, dateRange);
                chart.reloadData(exchangeRates);
            } catch (Exception e) {
                noExchangeRatesNotification.handle(e);
            }
        });
        form.fetchCurrencies(currencyService, noCurrenciesNotification);

        var localeSelector = new LocaleSelect(i18nProvider);
        localeSelector.addValueChangeListener(event -> {
            UI ui = UI.getCurrent();
            Locale newSelection = event.getValue();
            ui.setLocale(newSelection);
        });

        var drawerContent = new VerticalLayout(form, new FormLayout(localeSelector));
        drawerContent.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN); // FIXME: This doesn't work
        addToDrawer(drawerContent);
        setPrimarySection(Section.DRAWER);
    }
}
