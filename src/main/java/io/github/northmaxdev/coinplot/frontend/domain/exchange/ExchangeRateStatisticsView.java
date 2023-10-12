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

    private static final String MIN_FIELD_LABEL_KEY = "exchange-rate-statistic-view.min-field.label";
    private static final String MAX_FIELD_LABEL_KEY = "exchange-rate-statistic-view.max-field.label";
    private static final String AVERAGE_FIELD_LABEL_KEY = "exchange-rate-statistic-view.average-field.label";
    private static final String COUNT_FIELD_LABEL_KEY = "exchange-rate-statistic-view.count-field.label";

    private final BigDecimalField minField;
    private final BigDecimalField maxField;
    private final BigDecimalField averageField;
    private final IntegerField countField;

    public ExchangeRateStatisticsView() {
        minField = new BigDecimalField();
        maxField = new BigDecimalField();
        averageField = new BigDecimalField();
        countField = new IntegerField();

        minField.setReadOnly(true);
        maxField.setReadOnly(true);
        averageField.setReadOnly(true);
        countField.setReadOnly(true);

        add(minField, maxField, averageField, countField);
    }

    public void visualize(@Nonnull ExchangeRateStatistics statistics) {
        Objects.requireNonNull(statistics);

        statistics.getMin()
                .ifPresentOrElse(minField::setValue, minField::clear);

        statistics.getMax()
                .ifPresentOrElse(maxField::setValue, maxField::clear);

        averageField.setValue(statistics.getAverage());
        countField.setValue(statistics.getCount());
    }

    public void clear() {
        minField.clear();
        maxField.clear();
        averageField.clear();
        countField.clear();
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        minField.setLocale(event.getLocale());
        I18NUtilities.setLabel(minField, event, MIN_FIELD_LABEL_KEY);

        maxField.setLocale(event.getLocale());
        I18NUtilities.setLabel(maxField, event, MAX_FIELD_LABEL_KEY);

        averageField.setLocale(event.getLocale());
        I18NUtilities.setLabel(averageField, event, AVERAGE_FIELD_LABEL_KEY);

        // IntegerField does not have locale settings
        I18NUtilities.setLabel(countField, event, COUNT_FIELD_LABEL_KEY);
    }
}
