// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.exchange.DatelessExchange;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateBatch;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Set;

public final class ExchangeRateAnalyticsDashboard extends VerticalLayout implements LocaleChangeObserver {

    private final Select<ExchangeRateBatch> analysisTargetPicker;
    private final ExchangeRateAnalyticsVisualizer visualizer;

    public ExchangeRateAnalyticsDashboard() {
        super(Alignment.STRETCH);

        // Initialization in reverse order
        visualizer = new ExchangeRateAnalyticsVisualizer();

        analysisTargetPicker = new Select<>();
        analysisTargetPicker.setPrefixComponent(VaadinIcon.MONEY_EXCHANGE.create());
        analysisTargetPicker.setItemLabelGenerator(batch -> {
            DatelessExchange exchange = batch.getExchange();
            return exchange.getLabel();
        });
        analysisTargetPicker.addValueChangeListener(event -> {
            @Nullable ExchangeRateBatch selectedAnalysisTarget = event.getValue();
            if (selectedAnalysisTarget == null) {
                visualizer.clear();
            } else {
                visualizer.visualize(selectedAnalysisTarget);
            }
        });

        HorizontalLayout controlsBar = new HorizontalLayout(analysisTargetPicker);
        controlsBar.setPadding(false);
        controlsBar.setSpacing(true);

        add(controlsBar, visualizer);
        setPadding(true);
        setSpacing(true);
    }

    // Duplicates are functionally OK (stuff works),
    // but semantically redundant (not very useful).
    // Therefore, enforce uniqueness by requiring a Set.
    public void setAnalysisTargets(@Nonnull Set<ExchangeRateBatch> batches) {
        Set<ExchangeRateBatch> protectiveCopy = Set.copyOf(batches); // Implicit deep null-check(s)
        analysisTargetPicker.setItems(protectiveCopy); // This also fires a value change event that resets the selection
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        I18NUtilities.setLabel(analysisTargetPicker, event, "exchange-rate-analytics-dashboard.analysis-target-picker.label");
        visualizer.localeChange(event);
    }
}
