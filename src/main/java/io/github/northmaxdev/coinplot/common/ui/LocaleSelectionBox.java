// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.common.ui;

import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import jakarta.annotation.Nonnull;

import java.util.Locale;

public final class LocaleSelectionBox extends Select<Locale> implements LocaleChangeObserver {

    private final I18NProvider i18nProvider;

    public LocaleSelectionBox(@Nonnull I18NProvider i18nProvider) {
        this.i18nProvider = i18nProvider;

        setLabel(getFieldLabelTranslation());
        setItems(i18nProvider.getProvidedLocales());
        setItemLabelGenerator(this::getItemLabelTranslation);
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        setLabel(getFieldLabelTranslation());
    }

    private String getFieldLabelTranslation() {
        return getTranslation("locale-input.field-label");
    }

    private String getItemLabelTranslation(Locale locale) {
        return i18nProvider.getTranslation("locale-input.item-label", locale);
    }
}
