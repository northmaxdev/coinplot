// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.core;

import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.lang.TemporalOperation;
import jakarta.annotation.Nonnull;

import java.util.Locale;

public final class TemporalOperationRadioButtonGroup
        extends RadioButtonGroup<TemporalOperation>
        implements LocaleChangeObserver {

    public TemporalOperationRadioButtonGroup() {
        this(TemporalOperation.ADDITION);
    }

    public TemporalOperationRadioButtonGroup(@Nonnull TemporalOperation initialValue) {
        setItems(TemporalOperation.values());
        setItemLabelGenerator(op -> getTranslatedOperationLabel(op, getLocale()));
        setValue(initialValue);
        addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        setItemLabelGenerator(op -> getTranslatedOperationLabel(op, event.getLocale()));
    }

    private String getTranslatedOperationLabel(TemporalOperation op, Locale locale) {
        String propertyKey = switch (op) {
            case ADDITION -> "temporal-operation-radio-button-group.item-label.addition";
            case SUBTRACTION -> "temporal-operation-radio-button-group.item-label.subtraction";
        };

        return getTranslation(locale, propertyKey);
    }
}
