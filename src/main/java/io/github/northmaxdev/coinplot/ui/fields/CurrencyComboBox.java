// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.ui.fields;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.domain.currency.ExchangeRatesService;
import io.github.northmaxdev.coinplot.ui.I18nUtil;

import java.util.Collection;
import java.util.Currency;
import java.util.Locale;

public final class CurrencyComboBox extends ComboBox<Currency> implements LocaleChangeObserver {

    public CurrencyComboBox(Collection<Currency> currencies) {
        setItems(currencies);
    }

    public CurrencyComboBox(ExchangeRatesService exchangeRatesService) {
        this(exchangeRatesService.getSupportedCurrencies());
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        Locale newLocale = event.getLocale();
        setItemLabelGenerator(currency -> currency.getDisplayName(newLocale));
        I18nUtil.setHelperText(this, event, "currency-combo-box.helper-text");
    }
}
