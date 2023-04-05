// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.common.ui;

import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Locale;

public final class LocaleRadioButtonGroup extends RadioButtonGroup<Locale> implements LocaleChangeObserver {

    public LocaleRadioButtonGroup(
            @Nonnull I18NProvider i18nProvider,
            @Nonnull ValueChangeListener<ComponentValueChangeEvent<RadioButtonGroup<Locale>, Locale>> listener) {
        super(listener);

        setFieldLabelTranslation();
        setItemLabelGenerator(locale -> i18nProvider.getTranslation("locale-input.item-label", locale));

        List<Locale> supportedLocales = i18nProvider.getProvidedLocales();
        setItems(supportedLocales);
        if (!supportedLocales.isEmpty()) {
            // The default locale as per I18NProvider documentation is the first one anyway, but doing this explicitly
            // toggles the correct button (without it, it's in an unselected state, which is illegal).
            Locale defaultLocale = supportedLocales.get(0);
            setValue(defaultLocale);
        }
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
