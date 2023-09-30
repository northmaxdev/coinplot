// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.frontend.common.ThemableLayouts.Spacing;
import io.github.northmaxdev.coinplot.lang.chrono.LocalDateInterval;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;

public final class LocalDateIntervalPicker extends CustomField<LocalDateInterval> implements LocaleChangeObserver {

    private final DatePicker startDatePicker;
    private final DatePicker endDatePicker;

    ////////////////
    // Public API //
    ////////////////

    public LocalDateIntervalPicker() {
        startDatePicker = new DatePicker();
        endDatePicker = new DatePicker();

        // FIXME (Implementation): ValueChangeListener(s) for date chronology validation

        startDatePicker.setRequired(true);
        endDatePicker.setRequired(true);

        HorizontalLayout layout = ThemableLayouts.horizontal(Spacing.EXTRA_SMALL, startDatePicker, endDatePicker);
        add(layout);
    }

    public LocalDateIntervalPicker(@Nullable LocalDateInterval initialValue) {
        this();
        setValue(initialValue);
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        Locale newLocale = event.getLocale();
        startDatePicker.setLocale(newLocale);
        endDatePicker.setLocale(newLocale);
    }

    //////////////////////////
    // Implementation stuff //
    //////////////////////////

    @Override
    protected @Nullable LocalDateInterval generateModelValue() {
        // Listeners are responsible with ensuring the chronology is valid
        Optional<LocalDate> start = startDatePicker.getOptionalValue();
        Optional<LocalDate> end = endDatePicker.getOptionalValue();
        return start.isPresent() && end.isPresent() ? new LocalDateInterval(start.get(), end.get()) : null;
    }

    @Override
    protected void setPresentationValue(@Nullable LocalDateInterval newPresentationValue) {
        if (newPresentationValue == null) {
            startDatePicker.clear();
            endDatePicker.clear();
        } else {
            startDatePicker.setValue(newPresentationValue.start());
            endDatePicker.setValue(newPresentationValue.end());
        }
    }
}
