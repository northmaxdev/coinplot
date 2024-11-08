// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.ui.i18n;

import com.vaadin.flow.i18n.I18NProvider;
import io.github.northmaxdev.coinplot.util.Locales;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Component
public final class I18nProviderImpl implements I18NProvider {

    private static final String RESOURCE_BUNDLE_PREFIX = "i18n/i18n";
    private static final List<Locale> SUPPORTED_LOCALES = List.of(
            // The first item in this list is the default option.
            // The rest are ordered arbitrarily.
            Locales.ENGLISH_US,
            Locales.ENGLISH_UK,
            Locales.HEBREW_ISRAEL,
            Locales.RUSSIAN_RUSSIA
    );

    @Override
    public List<Locale> getProvidedLocales() {
        return SUPPORTED_LOCALES;
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_PREFIX, locale);
            String s = bundle.getString(key);
            return params.length > 0 ? s.formatted(params) : s;
        } catch (MissingResourceException e) {
            // https://vaadin.com/docs/latest/advanced/i18n-localization#provider-sample-for-translation
            return "NO '%s' TRANSLATION: %s".formatted(locale, key);
        }
    }
}
