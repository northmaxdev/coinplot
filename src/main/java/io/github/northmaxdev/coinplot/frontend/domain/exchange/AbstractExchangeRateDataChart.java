// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.Series;
import io.github.northmaxdev.coinplot.backend.core.exchange.DatelessExchange;
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

    // The "IgnoringName" part means that the series name is configured outside this method,
    // and therefore the implementing class should not care about it.
    protected abstract @Nonnull Series mapBatchToSeriesIgnoringName(@Nonnull ExchangeRateBatch batch);

    ////////////////
    // Public API //
    ////////////////

    public final void visualize(@Nonnull Set<ExchangeRateBatch> exchangeRateBatches) {
        Objects.requireNonNull(exchangeRateBatches);

        // In terms of type semantics and interface contracts, we don't really need a List here,
        // but it's what the Vaadin Charts API requires.
        List<Series> series = exchangeRateBatches.stream()
                .map(batch -> {
                    DatelessExchange exchange = batch.getExchange();
                    Series s = mapBatchToSeriesIgnoringName(batch);
                    s.setName(exchange.toString()); // Remember that toString() may not always be suitable for UI
                    return s;
                })
                .toList();

        Configuration config = getConfiguration();
        config.setSeries(series);

        drawChart(true); // See Configuration::setSeries JavaDoc for info
    }
}
