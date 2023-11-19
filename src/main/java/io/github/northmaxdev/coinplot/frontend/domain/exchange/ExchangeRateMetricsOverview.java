// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateBatch;
import io.github.northmaxdev.coinplot.frontend.common.LocalizedVisualizer;
import io.github.northmaxdev.coinplot.frontend.common.NumericChangeReport;
import io.github.northmaxdev.coinplot.frontend.common.NumericExtremesReport;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import jakarta.annotation.Nonnull;

import java.util.Objects;

public final class ExchangeRateMetricsOverview
        extends TabSheet
        implements LocalizedVisualizer<ExchangeRateBatch> {

    private final Tab latestChangeTab;
    private final NumericChangeReport latestChangeReport;
    private final Tab extremesTab;
    private final NumericExtremesReport extremesReport;

    public ExchangeRateMetricsOverview() {
        latestChangeTab = new Tab();
        latestChangeReport = new NumericChangeReport();
        add(latestChangeTab, latestChangeReport);

        extremesTab = new Tab();
        extremesReport = new NumericExtremesReport();
        add(extremesTab, extremesReport);
    }

    @Override
    public void visualize(@Nonnull ExchangeRateBatch batch) {
        Objects.requireNonNull(batch);
        latestChangeReport.visualizeOrClear(batch.getLatestAvailableChange());
        extremesReport.visualizeOrClear(batch.getValueExtremes());
    }

    @Override
    public void clear() {
        latestChangeReport.clear();
        extremesReport.clear();
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        I18NUtilities.setLabel(latestChangeTab, event, "exchange-rate-metrics-overview.latest-change-tab.label");
        latestChangeReport.localeChange(event);

        I18NUtilities.setLabel(extremesTab, event, "exchange-rate-metrics-overview.extremes-tab.label");
        extremesReport.localeChange(event);
    }
}
