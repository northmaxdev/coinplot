// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRate;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateBatch;
import jakarta.annotation.Nonnull;

import java.util.Set;

public final class ExchangeRateDataVisualizer extends VerticalLayout {

    private final ExchangeRateDynamicsChart dynamicsChart;
    private final ExchangeRateLatestChangeChart latestChangeChart;
    private final ExchangeRateExtremesChart extremesChart;

    public ExchangeRateDataVisualizer() {
        dynamicsChart = new ExchangeRateDynamicsChart();
        latestChangeChart = new ExchangeRateLatestChangeChart();
        extremesChart = new ExchangeRateExtremesChart();

        HorizontalLayout metadataVisualizationPanel = new HorizontalLayout(latestChangeChart, extremesChart);
        metadataVisualizationPanel.setPadding(false); // This is so it's horizontally in line with dynamicsChart
        add(dynamicsChart, metadataVisualizationPanel);

        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);
        setJustifyContentMode(JustifyContentMode.BETWEEN);
    }

    public void visualize(@Nonnull Set<ExchangeRate> dataset) {
        Set<ExchangeRateBatch> batches = ExchangeRateBatch.multipleFromDataset(dataset);

        dynamicsChart.visualize(batches);
        latestChangeChart.visualize(batches);
        extremesChart.visualize(batches);
    }
}
