// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeRateStatistics;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import jakarta.annotation.Nonnull;

import java.util.Objects;

public final class ExchangeRateStatisticsView extends HorizontalLayout implements LocaleChangeObserver {

    private static final String MIN_VALUE_FIELD_LABEL_KEY = "exchange-rate-statistic-view.min-value-field.label";
    private static final String MAX_VALUE_FIELD_LABEL_KEY = "exchange-rate-statistic-view.max-value-field.label";
    private static final String AVERAGE_VALUE_FIELD_LABEL_KEY = "exchange-rate-statistic-view.average-value-field.label";
    private static final String EARLIEST_VALUE_FIELD_LABEL_KEY = "exchange-rate-statistic-view.earliest-value-field.label";
    private static final String LATEST_VALUE_FIELD_LABEL_KEY = "exchange-rate-statistic-view.latest-value-field.label";
    private static final String VALUE_COUNT_FIELD_LABEL_KEY = "exchange-rate-statistic-view.value-count-field.label";

    private final BigDecimalField minValueField;
    private final BigDecimalField maxValueField;
    private final BigDecimalField averageValueField;
    private final BigDecimalField earliestValueField;
    private final BigDecimalField latestValueField;
    private final IntegerField valueCountField;

    public ExchangeRateStatisticsView() {
        minValueField = new BigDecimalField();
        maxValueField = new BigDecimalField();
        averageValueField = new BigDecimalField();
        earliestValueField = new BigDecimalField();
        latestValueField = new BigDecimalField();
        valueCountField = new IntegerField();

        minValueField.setReadOnly(true);
        maxValueField.setReadOnly(true);
        averageValueField.setReadOnly(true);
        earliestValueField.setReadOnly(true);
        latestValueField.setReadOnly(true);
        valueCountField.setReadOnly(true);

        add(minValueField, maxValueField, averageValueField, earliestValueField, latestValueField, valueCountField);
    }

    public void visualize(@Nonnull ExchangeRateStatistics statistics) {
        Objects.requireNonNull(statistics);

        statistics.getMinValue()
                .ifPresentOrElse(minValueField::setValue, minValueField::clear);
        statistics.getMaxValue()
                .ifPresentOrElse(maxValueField::setValue, maxValueField::clear);
        averageValueField.setValue(statistics.getAverageValue());
        statistics.getEarliestValue()
                .ifPresentOrElse(earliestValueField::setValue, earliestValueField::clear);
        statistics.getLatestValue()
                .ifPresentOrElse(latestValueField::setValue, latestValueField::clear);
        valueCountField.setValue(statistics.getValueCount());
    }

    public void clear() {
        minValueField.clear();
        maxValueField.clear();
        averageValueField.clear();
        earliestValueField.clear();
        latestValueField.clear();
        valueCountField.clear();
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        minValueField.setLocale(event.getLocale());
        I18NUtilities.setLabel(minValueField, event, MIN_VALUE_FIELD_LABEL_KEY);

        maxValueField.setLocale(event.getLocale());
        I18NUtilities.setLabel(maxValueField, event, MAX_VALUE_FIELD_LABEL_KEY);

        averageValueField.setLocale(event.getLocale());
        I18NUtilities.setLabel(averageValueField, event, AVERAGE_VALUE_FIELD_LABEL_KEY);

        earliestValueField.setLocale(event.getLocale());
        I18NUtilities.setLabel(earliestValueField, event, EARLIEST_VALUE_FIELD_LABEL_KEY);

        latestValueField.setLocale(event.getLocale());
        I18NUtilities.setLabel(latestValueField, event, LATEST_VALUE_FIELD_LABEL_KEY);

        // IntegerField does not have locale settings
        I18NUtilities.setLabel(valueCountField, event, VALUE_COUNT_FIELD_LABEL_KEY);
    }
}
