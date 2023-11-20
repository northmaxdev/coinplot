// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.DataProvider;
import io.github.northmaxdev.coinplot.backend.core.exchange.DatelessExchange;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRate;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateBatch;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateService;
import io.github.northmaxdev.coinplot.frontend.I18NUtilities;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Objects;
import java.util.Set;

public final class ExchangeRateAnalyticsDashboard
        extends SplitLayout
        implements LocaleChangeObserver {

    private final ExchangeBatchSubmissionForm requestAssemblyForm;
    private final Select<ExchangeRateBatch> analysisTargetPicker;
    private final ExchangeRateDynamicsChart dynamicsChart;
    private final ExchangeRateMetricsOverview metricsOverview;

    public ExchangeRateAnalyticsDashboard(@Nonnull DataProvider dataProvider) {
        super(Orientation.HORIZONTAL);
        Objects.requireNonNull(dataProvider);

        // Initialize in reverse order
        metricsOverview = new ExchangeRateMetricsOverview();
        dynamicsChart = new ExchangeRateDynamicsChart();

        analysisTargetPicker = new Select<>();
        analysisTargetPicker.setPrefixComponent(VaadinIcon.MONEY_EXCHANGE.create());
        analysisTargetPicker.setItemLabelGenerator(batch -> {
            DatelessExchange exchange = batch.getExchange();
            return exchange.getLabel();
        });
        analysisTargetPicker.addValueChangeListener(event -> {
            @Nullable ExchangeRateBatch selectedAnalysisTarget = event.getValue();
            dynamicsChart.visualizeOrClear(selectedAnalysisTarget);
            metricsOverview.visualizeOrClear(selectedAnalysisTarget);
        });

        requestAssemblyForm = new ExchangeBatchSubmissionForm(dataProvider.getCurrencyService(), desiredExchanges -> {
            ExchangeRateService service = dataProvider.getExchangeRateService();
            Set<ExchangeRate> dataset = service.getAvailableExchangeRates(desiredExchanges);
            Set<ExchangeRateBatch> exchangeRateBatches = ExchangeRateBatch.multipleFromDataset(dataset);
            analysisTargetPicker.setItems(exchangeRateBatches);
        });

        // The components of both sides should be wrapped identically
        // for the sake of visual symmetry.

        VerticalLayout primaryContent = wrap(analysisTargetPicker, dynamicsChart, metricsOverview);
        addToPrimary(primaryContent);

        VerticalLayout secondaryContent = wrap(requestAssemblyForm);
        addToSecondary(secondaryContent);
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        requestAssemblyForm.localeChange(event);
        I18NUtilities.setLabel(analysisTargetPicker, event, "exchange-rate-analytics-dashboard.analysis-target-picker.label");
        dynamicsChart.localeChange(event);
        metricsOverview.localeChange(event);
    }

    private static @Nonnull VerticalLayout wrap(@Nonnull Component... components) {
        VerticalLayout panel = new VerticalLayout(components);
        panel.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);
        panel.setSpacing(true);
        panel.setPadding(true);
        return panel;
    }
}
