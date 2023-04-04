// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.common.ui;

import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import jakarta.annotation.Nonnull;

import java.util.Locale;

public final class LocaleSelect extends Select<Locale> implements LocaleChangeObserver {

    public LocaleSelect(@Nonnull I18NProvider i18nProvider) {
        setFieldLabelTranslation();
        setItems(i18nProvider.getProvidedLocales());
        setItemLabelGenerator(locale -> i18nProvider.getTranslation("locale-input.item-label", locale));
        setEmptySelectionAllowed(false);
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
