// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.i18n;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasHelper;
import com.vaadin.flow.component.HasLabel;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.HasValidation;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import jakarta.annotation.Nonnull;

import java.util.Locale;
import java.util.function.BiConsumer;

// Make an exception for the project's naming convention (e.g., Strings or Ints)
// due to "I18N" being too ambiguous as a class name.
public final class I18NUtilities {

    private I18NUtilities() {
        throw new UnsupportedOperationException();
    }

    // locale and propertyKey are not annotated due to getTranslation's ambiguity regarding the same parameters

    ///////////
    // Label //
    ///////////

    public static <C extends Component & HasLabel> void setLabel(
            @Nonnull C component,
            Locale locale,
            String propertyKey) {
        set(component, locale, propertyKey, C::setLabel);
    }

    public static <C extends Component & HasLabel> void setLabel(
            @Nonnull C component,
            @Nonnull LocaleChangeEvent localeChangeEvent,
            String propertyKey) {
        setLabel(component, localeChangeEvent.getLocale(), propertyKey);
    }

    //////////
    // Text //
    //////////

    public static <C extends Component & HasText> void setText(
            @Nonnull C component,
            Locale locale,
            String propertyKey) {
        set(component, locale, propertyKey, C::setText);
    }

    public static <C extends Component & HasText> void setText(
            @Nonnull C component,
            LocaleChangeEvent localeChangeEvent,
            String propertyKey) {
        setText(component, localeChangeEvent.getLocale(), propertyKey);
    }

    /////////////////
    // Helper Text //
    /////////////////

    public static <C extends Component & HasHelper> void setHelperText(
            @Nonnull C component,
            Locale locale,
            String propertyKey) {
        set(component, locale, propertyKey, C::setHelperText);
    }

    public static <C extends Component & HasHelper> void setHelperText(
            @Nonnull C component,
            @Nonnull LocaleChangeEvent localeChangeEvent,
            String propertyKey) {
        setHelperText(component, localeChangeEvent.getLocale(), propertyKey);
    }

    ///////////////////
    // Error Message //
    ///////////////////

    public static <C extends Component & HasValidation> void setErrorMessage(
            @Nonnull C component,
            Locale locale,
            String propertyKey) {
        set(component, locale, propertyKey, C::setErrorMessage);
    }

    public static <C extends Component & HasValidation> void setErrorMessage(
            @Nonnull C component,
            @Nonnull LocaleChangeEvent localeChangeEvent,
            String propertyKey) {
        setErrorMessage(component, localeChangeEvent.getLocale(), propertyKey);
    }

    //////////
    // Impl //
    //////////

    private static <C extends Component> void set(
            @Nonnull C component,
            Locale locale,
            String propertyKey,
            BiConsumer<C, String> componentTypeSetterReference) {
        String s = component.getTranslation(locale, propertyKey);
        componentTypeSetterReference.accept(component, s);
    }
}
