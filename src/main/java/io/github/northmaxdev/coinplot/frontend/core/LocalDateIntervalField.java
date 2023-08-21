// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.core;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.frontend.core.ThemableLayouts.Spacing;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import io.github.northmaxdev.coinplot.lang.chrono.LocalDateInterval;
import jakarta.annotation.Nullable;

import java.time.Period;

public final class LocalDateIntervalField extends CustomField<LocalDateInterval> implements LocaleChangeObserver {

    private static final String START_PICKER_LABEL_KEY = "local-date-interval-field.label.start-picker";
    private static final String PERIOD_FIELD_LABEL_KEY = "local-date-interval-field.label.period-field";

    private final DatePicker startPicker;
    private final PeriodField periodField;

    public LocalDateIntervalField() {
        this(null);
    }

    public LocalDateIntervalField(@Nullable LocalDateInterval initialValue) {
        this.startPicker = new DatePicker();
        this.periodField = new PeriodField();

        startPicker.setRequired(true);
        HorizontalLayout layout = ThemableLayouts.createHorizontal(Spacing.MEDIUM, startPicker, periodField);
        add(layout);

        setValue(initialValue);
    }

    //////////////////////
    // Model/view stuff //
    //////////////////////

    @Override
    protected @Nullable LocalDateInterval generateModelValue() {
        Period periodToAdd = periodField.getValue();
        return startPicker.getOptionalValue()
                .map(start -> LocalDateInterval.ofAddition(start, periodToAdd))
                .orElse(null);
    }

    @Override
    protected void setPresentationValue(@Nullable LocalDateInterval newPresentationValue) {
        if (newPresentationValue == null) {
            startPicker.clear();
            periodField.clear();
        } else {
            startPicker.setValue(newPresentationValue.start());
            periodField.setValue(newPresentationValue.toPeriod());
        }
    }

    //////////
    // I18N //
    //////////

    // This also gets triggered on component init
    @Override
    public void localeChange(LocaleChangeEvent event) {
        startPicker.setLocale(event.getLocale());
        I18NUtilities.setLabel(startPicker, event, START_PICKER_LABEL_KEY);

        periodField.localeChange(event);
        I18NUtilities.setLabel(periodField, event, PERIOD_FIELD_LABEL_KEY);
    }
}
