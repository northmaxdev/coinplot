// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.exchange.DatelessExchange;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRate;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateBatch;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Set;

public final class ExchangeRateAnalyticsVisualizer extends VerticalLayout implements LocaleChangeObserver {

    private static final String BATCH_PICKER_LABEL_KEY = "exchange-rate-analytics-visualizer.batch-picker.label";

    private final ExchangeRateDynamicsChart dynamicsChart;
    private final ExchangeRateBatchMetricsReport metricsReport;
    private final Select<ExchangeRateBatch> batchPicker;

    public ExchangeRateAnalyticsVisualizer() {
        dynamicsChart = new ExchangeRateDynamicsChart();
        metricsReport = new ExchangeRateBatchMetricsReport();

        batchPicker = new Select<>();
        batchPicker.setPrefixComponent(VaadinIcon.MONEY_EXCHANGE.create());
        batchPicker.setItemLabelGenerator(batch -> {
            DatelessExchange exchange = batch.getExchange();
            return exchange.getLabel();
        });
        batchPicker.addValueChangeListener(event -> {
            @Nullable ExchangeRateBatch selectedBatch = event.getValue();
            if (selectedBatch == null) {
                metricsReport.clear();
            } else {
                metricsReport.load(selectedBatch);
            }
        });

        add(dynamicsChart, batchPicker, metricsReport);
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
        setJustifyContentMode(JustifyContentMode.BETWEEN);
    }

    public void visualize(@Nonnull Set<ExchangeRate> dataset) {
        Set<ExchangeRateBatch> batches = ExchangeRateBatch.multipleFromDataset(dataset);

        dynamicsChart.visualize(batches);
        batchPicker.setItems(batches);
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        dynamicsChart.localeChange(event);
        metricsReport.localeChange(event);
        I18NUtilities.setLabel(batchPicker, event, BATCH_PICKER_LABEL_KEY);
    }
}
