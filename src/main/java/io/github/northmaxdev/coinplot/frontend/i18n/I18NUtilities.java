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

    ///////////
    // Label //
    ///////////

    public static <C extends Component & HasLabel> void setLabel(
            @Nonnull C component,
            @Nonnull Locale locale,
            @Nonnull String key) {
        set(component, locale, key, C::setLabel);
    }

    public static <C extends Component & HasLabel> void setLabel(
            @Nonnull C component,
            @Nonnull LocaleChangeEvent event,
            @Nonnull String key) {
        setLabel(component, event.getLocale(), key);
    }

    //////////
    // Text //
    //////////

    public static <C extends Component & HasText> void setText(
            @Nonnull C component,
            @Nonnull Locale locale,
            @Nonnull String key) {
        set(component, locale, key, C::setText);
    }

    public static <C extends Component & HasText> void setText(
            @Nonnull C component,
            @Nonnull LocaleChangeEvent event,
            @Nonnull String key) {
        setText(component, event.getLocale(), key);
    }

    /////////////////
    // Helper Text //
    /////////////////

    public static <C extends Component & HasHelper> void setHelperText(
            @Nonnull C component,
            @Nonnull Locale locale,
            @Nonnull String key) {
        set(component, locale, key, C::setHelperText);
    }

    public static <C extends Component & HasHelper> void setHelperText(
            @Nonnull C component,
            @Nonnull LocaleChangeEvent event,
            @Nonnull String key) {
        setHelperText(component, event.getLocale(), key);
    }

    ///////////////////
    // Error Message //
    ///////////////////

    public static <C extends Component & HasValidation> void setErrorMessage(
            @Nonnull C component,
            @Nonnull Locale locale,
            @Nonnull String key) {
        set(component, locale, key, C::setErrorMessage);
    }

    public static <C extends Component & HasValidation> void setErrorMessage(
            @Nonnull C component,
            @Nonnull LocaleChangeEvent event,
            @Nonnull String key) {
        setErrorMessage(component, event.getLocale(), key);
    }

    ////////////////////////////
    // Implementation details //
    ////////////////////////////

    private static <C extends Component> void set(
            @Nonnull C component,
            @Nonnull Locale locale,
            @Nonnull String key,
            @Nonnull BiConsumer<C, String> componentSetter) {
        // Explicit null-checks are omitted for brevity
        String s = component.getTranslation(locale, key);
        componentSetter.accept(component, s);
    }
}
