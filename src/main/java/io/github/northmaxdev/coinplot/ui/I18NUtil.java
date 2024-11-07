// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasHelper;
import com.vaadin.flow.component.HasLabel;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.i18n.LocaleChangeEvent;

import java.util.Locale;
import java.util.function.BiConsumer;

public final class I18NUtil {

    private I18NUtil() {
        throw new UnsupportedOperationException();
    }

    ///////////
    // Label //
    ///////////

    public static <C extends Component & HasLabel> void setLabel(C component, Locale locale, String key) {
        set(component, locale, key, C::setLabel);
    }

    public static <C extends Component & HasLabel> void setLabel(C component, LocaleChangeEvent event, String key) {
        setLabel(component, event.getLocale(), key);
    }

    //////////
    // Text //
    //////////

    public static <C extends Component & HasText> void setText(C component, Locale locale, String key) {
        set(component, locale, key, C::setText);
    }

    public static <C extends Component & HasText> void setText(C component, LocaleChangeEvent event, String key) {
        setText(component, event.getLocale(), key);
    }

    /////////////////
    // Helper Text //
    /////////////////

    public static <C extends Component & HasHelper> void setHelperText(C component, Locale locale, String key) {
        set(component, locale, key, C::setHelperText);
    }

    public static <C extends Component & HasHelper> void setHelperText(C component, LocaleChangeEvent event, String key) {
        setHelperText(component, event.getLocale(), key);
    }

    //////////
    // Impl //
    //////////

    private static <C extends Component> void set(C component, Locale locale, String key, BiConsumer<C, String> componentSetter) {
        String uiText = component.getTranslation(locale, key);
        componentSetter.accept(component, uiText);
    }
}
