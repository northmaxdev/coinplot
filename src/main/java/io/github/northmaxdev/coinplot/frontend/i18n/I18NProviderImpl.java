// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.i18n;

import com.vaadin.flow.i18n.I18NProvider;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

// Using the "impl" suffix is normally against the project's naming conventions,
// but in this case the exception is made to signal that this class is
// "the" implementation for the given framework interface.
@Component
public final class I18NProviderImpl implements I18NProvider {

    private static final String RESOURCE_BUNDLE_PREFIX = "i18n/i18n";
    private static final List<Locale> SUPPORTED_LOCALES = List.of(
            // The first item in this list is the default option
            Locale.of("en", "US"),
            Locale.of("ru", "RU"),
            Locale.of("he", "IL")
    );

    @Override
    public @Nonnull List<Locale> getProvidedLocales() {
        return SUPPORTED_LOCALES;
    }

    @Override
    public @Nonnull String getTranslation(@Nonnull String key, @Nonnull Locale locale, @Nonnull Object... params) {
        // Explicit null-checks are omitted for performance
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_PREFIX, locale);
            String s = bundle.getString(key);
            return params.length > 0 ? s.formatted(params) : s;
        } catch (MissingResourceException e) {
            // For more info: https://vaadin.com/docs/latest/advanced/i18n-localization#provider-sample-for-translation
            return "!No '%s' translation: %s".formatted(locale.getLanguage(), key);
        }
    }
}
