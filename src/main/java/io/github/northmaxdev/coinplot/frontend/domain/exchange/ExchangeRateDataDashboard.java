// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.DataProvider;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRate;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateBatch;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateService;
import jakarta.annotation.Nonnull;

import java.util.Objects;
import java.util.Set;

public final class ExchangeRateDataDashboard extends SplitLayout implements LocaleChangeObserver {

    private final ExchangeRateDynamicsChart dynamicsChart;
    private final ExchangeRateLatestChangeChart latestChangeChart;
    private final ExchangeRateExtremesChart extremesChart;

    private final ExchangeRateService service;
    private final ExchangeBatchSubmissionForm requestAssemblyForm;

    public ExchangeRateDataDashboard(@Nonnull DataProvider dataProvider) {
        this(dataProvider.getCurrencyService(), dataProvider.getExchangeRateService());
    }

    public ExchangeRateDataDashboard(@Nonnull CurrencyService currencyService, @Nonnull ExchangeRateService exchangeRateService) {
        super(Orientation.HORIZONTAL);

        dynamicsChart = new ExchangeRateDynamicsChart();
        latestChangeChart = new ExchangeRateLatestChangeChart();
        extremesChart = new ExchangeRateExtremesChart();

        service = Objects.requireNonNull(exchangeRateService);
        requestAssemblyForm = new ExchangeBatchSubmissionForm(currencyService, exchangeBatch -> {
            Set<ExchangeRate> dataset = service.getAvailableExchangeRates(exchangeBatch);
            Set<ExchangeRateBatch> batches = ExchangeRateBatch.multipleFromDataset(dataset);

            dynamicsChart.visualize(batches);
            latestChangeChart.visualize(batches);
            extremesChart.visualize(batches);
        });

        VerticalLayout visualizationPanel = new VerticalLayout(dynamicsChart, new HorizontalLayout(latestChangeChart, extremesChart));
        visualizationPanel.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);
        visualizationPanel.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        addToPrimary(visualizationPanel);

        VerticalLayout inputPanel = new VerticalLayout(requestAssemblyForm);
        inputPanel.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);
        inputPanel.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        addToSecondary(inputPanel);
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        requestAssemblyForm.localeChange(event);
    }
}
