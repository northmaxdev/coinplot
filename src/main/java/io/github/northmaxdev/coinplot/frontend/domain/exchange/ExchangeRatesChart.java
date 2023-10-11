// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.Series;
import io.github.northmaxdev.coinplot.backend.core.exchange.DatelessExchange;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateStatistics;
import io.github.northmaxdev.coinplot.lang.chrono.Instants;
import jakarta.annotation.Nonnull;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public final class ExchangeRatesChart extends Chart {

    // Basically just a regular DataSeries that keeps a reference to its data source (an ExchangeRateStatistics instance)
    public static final class ExchangeRateSeries extends DataSeries {

        private final @Nonnull ExchangeRateStatistics statistics;

        public ExchangeRateSeries(@Nonnull ExchangeRateStatistics statistics) {
            this.statistics = Objects.requireNonNull(statistics);

            DatelessExchange exchange = this.statistics.getExchange();
            setName(exchange.toString()); // Remember that toString contractually might return something not suitable for UIs

            List<DataSeriesItem> data = this.statistics.getRateValueChronology()
                    .entrySet()
                    // TODO (Performance): Consider stream parallelization
                    .stream() // FIXME (Implementation): Does this keep the sorted state?
                    .map(entry -> {
                        Instant x = Instants.toInstant(entry::getKey);
                        Number y = entry.getValue();
                        return new DataSeriesItem(x, y);
                    })
                    .toList();
            setData(data);
        }

        public @Nonnull ExchangeRateStatistics getStatistics() {
            return statistics;
        }
    }

    public ExchangeRatesChart() {
        super(ChartType.LINE);
    }

    public void visualize(@Nonnull Collection<ExchangeRateStatistics> statistics) {
        // TODO (Performance): Consider stream parallelization
        List<Series> series = statistics.stream() // Implicit null-check
                .map(ExchangeRateSeries::new)
                .map(Series.class::cast)
                .toList();

        Configuration config = getConfiguration();
        config.setSeries(series);
        drawChart(true); // FIXME (Implementation): Is this needed? The Configuration::setSeries JavaDoc effectively says "yes"
    }
}
