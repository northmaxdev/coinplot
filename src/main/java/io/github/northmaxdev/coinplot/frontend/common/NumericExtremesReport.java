// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import io.github.northmaxdev.coinplot.lang.math.NumericExtremes;
import jakarta.annotation.Nonnull;

import java.util.Locale;
import java.util.Objects;

public final class NumericExtremesReport
        extends FormLayout
        implements LocalizedVisualizer<NumericExtremes> {

    private final BigDecimalField minField;
    private final BigDecimalField maxField;

    public NumericExtremesReport() {
        minField = new BigDecimalField();
        minField.setReadOnly(true);
        minField.setPrefixComponent(VaadinIcon.ARROW_DOWN.create());
        add(minField);

        maxField = new BigDecimalField();
        maxField.setReadOnly(true);
        maxField.setPrefixComponent(VaadinIcon.ARROW_UP.create());
        add(maxField);
    }

    @Override
    public void visualize(@Nonnull NumericExtremes numericExtremes) {
        Objects.requireNonNull(numericExtremes);
        minField.setValue(numericExtremes.min());
        maxField.setValue(numericExtremes.max());
    }

    @Override
    public void clear() {
        minField.clear();
        maxField.clear();
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        Locale locale = event.getLocale();

        minField.setLocale(locale);
        I18NUtilities.setLabel(minField, locale, "numeric-extremes-report.min-field-label");

        maxField.setLocale(locale);
        I18NUtilities.setLabel(maxField, locale, "numeric-extremes-report.max-field-label");
    }
}
