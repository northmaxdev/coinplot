// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.router.Route;
import io.github.northmaxdev.coinplot.common.ui.LocaleSelectionBox;
import io.github.northmaxdev.coinplot.common.ui.ResourceFetchFailureNotification;
import io.github.northmaxdev.coinplot.currency.CurrencyService;
import io.github.northmaxdev.coinplot.exchange.ExchangeRateService;
import io.github.northmaxdev.coinplot.exchange.ExchangeRatesLineChart;
import io.github.northmaxdev.coinplot.exchange.ExchangeRatesRequestForm;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

@Route
public final class MainView extends AppLayout {

    private @Nullable Notification ratesFetchFailureNotification = null;

    @Autowired
    public MainView(
            CurrencyService currencyService,
            ExchangeRateService exchangeRateService,
            I18NProvider i18nProvider) {

        /////////////
        // Primary //
        /////////////

        var chart = new ExchangeRatesLineChart();
        setContent(new VerticalLayout(chart));

        ////////////
        // Drawer //
        ////////////

        ExchangeRatesRequestForm.FormInputConsumer onOKButtonClick = (base, targets, dateRange) -> {
            try {
                var exchangeRates = exchangeRateService.getExchangeRatesBetweenDates(base, targets, dateRange);
                chart.reloadData(exchangeRates);
            } catch (Exception e) {
                getRatesFetchFailureNotification().open();
            }
        };

        ExchangeRatesRequestForm form;
        try {
            form = ExchangeRatesRequestForm.withCurrencyData(currencyService, onOKButtonClick);
        } catch (Exception e) {
            // The FormInputConsumer here is not necessary as the button
            // will (should) remain disabled the entire time anyway
            form = ExchangeRatesRequestForm.withoutCurrencyData(onOKButtonClick);
            ResourceFetchFailureNotification.forCurrencies().open();
        }

        var localeSelector = new LocaleSelectionBox(i18nProvider);
        localeSelector.addValueChangeListener(event -> {
            UI ui = UI.getCurrent();
            Locale newlySelectedLocale = event.getValue();
            ui.setLocale(newlySelectedLocale);
        });

        var drawerContent = new VerticalLayout(form, new FormLayout(localeSelector));
        addToDrawer(drawerContent);
        setPrimarySection(Section.DRAWER);
    }

    private Notification getRatesFetchFailureNotification() {
        if (ratesFetchFailureNotification == null) {
            ratesFetchFailureNotification = ResourceFetchFailureNotification.forExchangeRates();
        }
        return ratesFetchFailureNotification;
    }
}
