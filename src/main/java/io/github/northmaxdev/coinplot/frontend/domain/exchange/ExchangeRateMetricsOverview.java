// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateBatch;
import io.github.northmaxdev.coinplot.frontend.common.LocalizedVisualizer;
import io.github.northmaxdev.coinplot.frontend.common.NumericChangeReport;
import io.github.northmaxdev.coinplot.frontend.common.NumericExtremesReport;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import jakarta.annotation.Nonnull;

import java.util.Locale;
import java.util.Objects;

public final class ExchangeRateMetricsOverview
        extends TabSheet
        implements LocalizedVisualizer<ExchangeRateBatch> {

    private static final class MiscMetricsReport
            extends FormLayout
            implements LocalizedVisualizer<ExchangeRateBatch> {

        private final DatePicker earliestDateField;
        private final DatePicker latestDateField;
        private final BigDecimalField earliestRateField;
        private final BigDecimalField latestRateField;

        public MiscMetricsReport() {
            earliestDateField = new DatePicker();
            earliestDateField.setReadOnly(true);
            earliestDateField.setPrefixComponent(VaadinIcon.TIME_BACKWARD.create());
            add(earliestDateField);

            latestDateField = new DatePicker();
            latestDateField.setReadOnly(true);
            latestDateField.setPrefixComponent(VaadinIcon.TIME_FORWARD.create());
            add(latestDateField);

            earliestRateField = new BigDecimalField();
            earliestRateField.setReadOnly(true);
            earliestRateField.setPrefixComponent(VaadinIcon.MONEY.create());
            add(earliestRateField);

            latestRateField = new BigDecimalField();
            latestRateField.setReadOnly(true);
            latestRateField.setPrefixComponent(VaadinIcon.MONEY.create());
            add(latestRateField);
        }

        @Override
        public void visualize(@Nonnull ExchangeRateBatch batch) {
            Objects.requireNonNull(batch);
            batch.getEarliestAvailableDate()
                    .ifPresentOrElse(earliestDateField::setValue, earliestDateField::clear);
            batch.getLatestAvailableDate()
                    .ifPresentOrElse(latestDateField::setValue, latestDateField::clear);
            batch.getEarliestAvailableRate()
                    .ifPresentOrElse(earliestRateField::setValue, earliestRateField::clear);
            batch.getLatestAvailableRate()
                    .ifPresentOrElse(latestRateField::setValue, latestRateField::clear);
        }

        @Override
        public void clear() {
            earliestDateField.clear();
            latestDateField.clear();
            earliestRateField.clear();
            latestRateField.clear();
        }

        @Override
        public void localeChange(@Nonnull LocaleChangeEvent event) {
            Locale locale = event.getLocale();

            earliestDateField.setLocale(locale);
            I18NUtilities.setLabel(earliestDateField, locale, "exchange-rate-metrics-overview.misc-metrics-report.earliest-date-field.label");

            latestDateField.setLocale(locale);
            I18NUtilities.setLabel(latestDateField, locale, "exchange-rate-metrics-overview.misc-metrics-report.latest-date-field.label");

            earliestRateField.setLocale(locale);
            I18NUtilities.setLabel(earliestRateField, locale, "exchange-rate-metrics-overview.misc-metrics-report.earliest-rate-field.label");

            latestRateField.setLocale(locale);
            I18NUtilities.setLabel(latestRateField, locale, "exchange-rate-metrics-overview.misc-metrics-report.latest-rate-field.label");
        }
    }

    private final Tab latestChangeTab;
    private final NumericChangeReport latestChangeReport;
    private final Tab extremesTab;
    private final NumericExtremesReport extremesReport;
    private final Tab miscMetricsTab;
    private final MiscMetricsReport miscMetricsReport;

    public ExchangeRateMetricsOverview() {
        latestChangeTab = new Tab();
        latestChangeReport = new NumericChangeReport();
        add(latestChangeTab, latestChangeReport);

        extremesTab = new Tab();
        extremesReport = new NumericExtremesReport();
        add(extremesTab, extremesReport);

        miscMetricsTab = new Tab();
        miscMetricsReport = new MiscMetricsReport();
        add(miscMetricsTab, miscMetricsReport);
    }

    @Override
    public void visualize(@Nonnull ExchangeRateBatch batch) {
        Objects.requireNonNull(batch);
        latestChangeReport.visualizeOrClear(batch.getLatestAvailableChange());
        extremesReport.visualizeOrClear(batch.getValueExtremes());
        miscMetricsReport.visualize(batch);
    }

    @Override
    public void clear() {
        latestChangeReport.clear();
        extremesReport.clear();
        miscMetricsReport.clear();
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        I18NUtilities.setLabel(latestChangeTab, event, "exchange-rate-metrics-overview.latest-change-tab.label");
        latestChangeReport.localeChange(event);

        I18NUtilities.setLabel(extremesTab, event, "exchange-rate-metrics-overview.extremes-tab.label");
        extremesReport.localeChange(event);

        I18NUtilities.setLabel(miscMetricsTab, event, "exchange-rate-metrics-overview.misc-metrics-tab.label");
        miscMetricsReport.localeChange(event);
    }
}
