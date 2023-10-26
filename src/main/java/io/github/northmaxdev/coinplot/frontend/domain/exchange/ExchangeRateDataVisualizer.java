// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.board.Board;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRate;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateBatch;
import jakarta.annotation.Nonnull;

import java.util.Set;

public final class ExchangeRateDataVisualizer extends Board {

    private final ExchangeRateDynamicsChart dynamicsChart;
    private final ExchangeRateLatestChangeChart latestChangeChart;
    private final ExchangeRateExtremesChart extremesChart;

    public ExchangeRateDataVisualizer() {
        dynamicsChart = new ExchangeRateDynamicsChart();
        latestChangeChart = new ExchangeRateLatestChangeChart();
        extremesChart = new ExchangeRateExtremesChart();

        addRow(dynamicsChart);
        addRow(latestChangeChart, extremesChart);
    }

    public void visualize(@Nonnull Set<ExchangeRate> dataset) {
        Set<ExchangeRateBatch> batches = ExchangeRateBatch.multipleFromDataset(dataset);

        dynamicsChart.visualize(batches);
        latestChangeChart.visualize(batches);
        extremesChart.visualize(batches);
    }
}
