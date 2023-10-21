// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.Series;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRate;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractExchangeRateSetChart extends Chart implements ExchangeRateDataVisualizer {

    //////////////////////////////////
    // Protected API for subclasses //
    //////////////////////////////////

    protected AbstractExchangeRateSetChart(@Nonnull ChartType chartType) {
        super(chartType);
    }

    protected abstract @Nonnull List<Series> createSeriesFromDataset(@Nonnull Set<ExchangeRate> dataset);

    ////////////////
    // Public API //
    ////////////////

    @Override
    public final void visualize(@Nonnull Set<ExchangeRate> dataset) {
        Objects.requireNonNull(dataset);
        List<Series> series = createSeriesFromDataset(dataset);

        Configuration chartConfig = getConfiguration();
        chartConfig.setSeries(series);
        drawChart(true); // FIXME: setSeries() JavaDoc says this needs to be called for redraws, check that later
    }
}
