// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.Series;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateBatch;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractExchangeRateDataChart extends Chart {

    //////////////////////////////////
    // Protected API for subclasses //
    //////////////////////////////////

    protected AbstractExchangeRateDataChart(@Nonnull ChartType chartType) {
        super(chartType);
    }

    // Each batch should represent a single series, i.e., a one-to-one mapping.
    // In terms of type semantics and interface contracts, we don't really need a List here,
    // but it's what the Vaadin Charts API requires.
    protected abstract @Nonnull List<Series> mapBatchesToSeries(@Nonnull Set<ExchangeRateBatch> exchangeRateBatches);

    ////////////////
    // Public API //
    ////////////////

    public final void visualize(@Nonnull Set<ExchangeRateBatch> exchangeRateBatches) {
        Objects.requireNonNull(exchangeRateBatches);
        List<Series> series = mapBatchesToSeries(exchangeRateBatches);

        Configuration config = getConfiguration();
        config.setSeries(series);

        drawChart(true); // FIXME: setSeries() JavaDoc says this needs to be called for redraws, check that later
    }
}
