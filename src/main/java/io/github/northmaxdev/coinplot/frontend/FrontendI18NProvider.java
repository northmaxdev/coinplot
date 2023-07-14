// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend;

import com.vaadin.flow.i18n.I18NProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Component
public final class FrontendI18NProvider implements I18NProvider {

    private static final String RESOURCE_BUNDLE_PREFIX = "i18n/i18n";
    private static final List<Locale> SUPPORTED_LOCALES = List.of(
            // The first item in this list is the default option
            new Locale("en"), // English
            new Locale("ru")  // Russian
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
            return params.length > 0
                    ? s.formatted(params)
                    : s;
        } catch (MissingResourceException e) {
            // TODO:
            //  The "exception string" syntax is unclear,
            //  test this if support for a new locale will be added.
            //  See the following URL for reference:
            //  https://vaadin.com/docs/latest/advanced/i18n-localization/#provider-sample-for-translation
            return "!Not found: key \"%s\" for locale \"%s\"".formatted(key, locale.getLanguage());
        }
    }
}
