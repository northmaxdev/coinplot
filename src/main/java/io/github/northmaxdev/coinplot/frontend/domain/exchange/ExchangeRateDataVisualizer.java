// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRate;
import jakarta.annotation.Nonnull;

import java.util.Set;

public final class ExchangeRateDataVisualizer extends VerticalLayout implements LocaleChangeObserver {

    private final ExchangeRateChart chart;
    private final ExchangeRateStatisticsView statisticsView;

    public ExchangeRateDataVisualizer() {
        chart = new ExchangeRateChart();
        statisticsView = new ExchangeRateStatisticsView();

        chart.addSeriesClickListener(event -> {
            // Should always be true if no external changes were made to the chart's series
            if (event.getSeries() instanceof ExchangeRateChart.ExchangeRateSeries exchangeRateSeries) { // nulls are 'false'
                statisticsView.visualize(exchangeRateSeries.getStatistics());
            } else {
                statisticsView.clear();
            }
        });

        add(chart, statisticsView);
    }

    public void visualize(@Nonnull Set<ExchangeRate> dataset) {
        chart.visualize(dataset);
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        statisticsView.localeChange(event);
    }
}
