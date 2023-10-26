// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.DataProvider;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRate;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateService;
import jakarta.annotation.Nonnull;

import java.util.Objects;
import java.util.Set;

public final class ExchangeRateDataDashboard extends SplitLayout implements LocaleChangeObserver {

    private final ExchangeRateService service;
    private final ExchangeRateDataVisualizer dataVisualizer;
    private final ExchangeBatchSubmissionForm requestAssemblyForm;

    public ExchangeRateDataDashboard(@Nonnull DataProvider dataProvider) {
        this(dataProvider.getCurrencyService(), dataProvider.getExchangeRateService());
    }

    public ExchangeRateDataDashboard(@Nonnull CurrencyService currencyService, @Nonnull ExchangeRateService exchangeRateService) {
        super(Orientation.HORIZONTAL);

        service = Objects.requireNonNull(exchangeRateService);
        dataVisualizer = new ExchangeRateDataVisualizer();
        requestAssemblyForm = new ExchangeBatchSubmissionForm(currencyService, exchangeBatch -> {
            Set<ExchangeRate> dataset = service.getAvailableExchangeRates(exchangeBatch);
            dataVisualizer.visualize(dataset);
        });

        VerticalLayout inputPanel = new VerticalLayout(requestAssemblyForm);
        inputPanel.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);

        addToPrimary(dataVisualizer);
        addToSecondary(inputPanel);
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        requestAssemblyForm.localeChange(event);
    }
}
