// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateBatch;
import jakarta.annotation.Nonnull;

import java.util.Objects;

public final class ExchangeRateAnalyticsVisualizer extends SplitLayout implements LocaleChangeObserver {

    private final ExchangeRateDynamicsChart dynamicsChart;
    private final ExchangeRateMetricsReport metricsReport;

    public ExchangeRateAnalyticsVisualizer() {
        dynamicsChart = new ExchangeRateDynamicsChart();
        metricsReport = new ExchangeRateMetricsReport();

        addToPrimary(dynamicsChart);
        addToSecondary(metricsReport);
    }

    public void visualize(@Nonnull ExchangeRateBatch batch) {
        Objects.requireNonNull(batch);

        dynamicsChart.plot(batch);
        metricsReport.load(batch);
    }

    public void clear() {
        dynamicsChart.clear();
        metricsReport.clear();
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        dynamicsChart.localeChange(event);
        metricsReport.localeChange(event);
    }
}
