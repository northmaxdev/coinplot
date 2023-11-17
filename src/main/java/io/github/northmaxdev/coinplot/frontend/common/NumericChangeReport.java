// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import io.github.northmaxdev.coinplot.lang.math.NumericChange;
import io.github.northmaxdev.coinplot.lang.math.Percentage;
import jakarta.annotation.Nonnull;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public final class NumericChangeReport
        extends FormLayout
        implements Visualizer<NumericChange>, LocaleChangeObserver {

    private static final String INCALCULABLE_PERCENTAGE_PLACEHOLDER = "∞";

    private final BigDecimalField initialValueField;
    private final BigDecimalField finalValueField;
    private final BigDecimalField differenceField;
    private final BigDecimalField percentageField;

    public NumericChangeReport() {
        initialValueField = new BigDecimalField();
        initialValueField.setReadOnly(true);
        initialValueField.setPrefixComponent(VaadinIcon.TIME_BACKWARD.create());
        add(initialValueField);

        finalValueField = new BigDecimalField();
        finalValueField.setReadOnly(true);
        finalValueField.setPrefixComponent(VaadinIcon.TIME_FORWARD.create());
        add(finalValueField);

        differenceField = new BigDecimalField();
        differenceField.setReadOnly(true);
        differenceField.setPrefixComponent(VaadinIcon.PLUS_MINUS.create());
        add(differenceField);

        percentageField = new BigDecimalField();
        percentageField.setReadOnly(true);
        percentageField.setPrefixComponent(VaadinIcon.BOOK_PERCENT.create());
        add(percentageField);
    }

    @Override
    public void visualize(@Nonnull NumericChange numericChange) {
        Objects.requireNonNull(numericChange);

        initialValueField.setValue(numericChange.getInitialValue());
        finalValueField.setValue(numericChange.getFinalValue());
        differenceField.setValue(numericChange.getDifference());

        Optional<Percentage> percentage = numericChange.getPercentageIfCalculable();
        percentage.ifPresentOrElse(p -> {
            percentageField.setPlaceholder(null);
            percentageField.setValue(p.getValue());
        }, () -> {
            percentageField.clear();
            percentageField.setPlaceholder(INCALCULABLE_PERCENTAGE_PLACEHOLDER);
        });
    }

    @Override
    public void clear() {
        initialValueField.clear();
        finalValueField.clear();
        differenceField.clear();
        percentageField.clear();
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        Locale locale = event.getLocale();

        initialValueField.setLocale(locale);
        I18NUtilities.setLabel(initialValueField, locale, "numeric-change-report.initial-value-field.label");

        finalValueField.setLocale(locale);
        I18NUtilities.setLabel(finalValueField, locale, "numeric-change-report.final-value-field.label");

        differenceField.setLocale(locale);
        I18NUtilities.setLabel(differenceField, locale, "numeric-change-report.difference-field.label");

        percentageField.setLocale(locale);
        I18NUtilities.setLabel(percentageField, locale, "numeric-change-report.percentage-field.label");
    }
}
