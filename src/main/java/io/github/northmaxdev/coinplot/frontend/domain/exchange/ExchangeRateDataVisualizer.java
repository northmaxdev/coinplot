// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRate;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateBatch;
import jakarta.annotation.Nonnull;

import java.util.Set;

public final class ExchangeRateDataVisualizer extends VerticalLayout implements LocaleChangeObserver {

    private final ExchangeRateDynamicsChart dynamicsChart;
    private final ExchangeRateMetadataReport metadataReport;

    public ExchangeRateDataVisualizer() {
        dynamicsChart = new ExchangeRateDynamicsChart();
        metadataReport = new ExchangeRateMetadataReport();

        add(dynamicsChart, metadataReport);
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
        setJustifyContentMode(JustifyContentMode.BETWEEN);
    }

    public void visualize(@Nonnull Set<ExchangeRate> dataset) {
        Set<ExchangeRateBatch> batches = ExchangeRateBatch.multipleFromDataset(dataset);

        dynamicsChart.visualize(batches);
        metadataReport.setItems(batches);
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        dynamicsChart.localeChange(event);
        metadataReport.localeChange(event);
    }
}
