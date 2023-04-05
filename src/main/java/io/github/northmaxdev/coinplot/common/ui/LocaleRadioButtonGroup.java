// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.common.ui;

import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import jakarta.annotation.Nonnull;

import java.util.Locale;

public final class LocaleRadioButtonGroup extends RadioButtonGroup<Locale> implements LocaleChangeObserver {

    public LocaleRadioButtonGroup(@Nonnull I18NProvider i18nProvider) {
        setFieldLabelTranslation();
        setItemLabelGenerator(locale -> i18nProvider.getTranslation("locale-input.item-label", locale));
        setItems(i18nProvider.getProvidedLocales());
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        setFieldLabelTranslation();
    }

    private void setFieldLabelTranslation() {
        @Nonnull String label = getTranslation("locale-input.field-label");
        setLabel(label);
    }
}
