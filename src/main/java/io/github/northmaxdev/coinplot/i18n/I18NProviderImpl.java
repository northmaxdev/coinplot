// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.i18n;

import com.vaadin.flow.i18n.I18NProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Component
public final class I18NProviderImpl implements I18NProvider {

    private static final String RESOURCE_BUNDLE_BASE_NAME = "i18n/i18n";

    private static final Locale ENGLISH = new Locale("en");

    @Override
    public List<Locale> getProvidedLocales() {
        return List.of(ENGLISH);
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME, locale);
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return "!{%s}".formatted(key);
        }
    }
}
