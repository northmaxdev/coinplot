// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.ui.inputs;

import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.domain.currency.ExchangeRatesService;
import io.github.northmaxdev.coinplot.ui.I18nUtil;

import java.util.Collection;
import java.util.Currency;
import java.util.Locale;

public final class CurrencyMultiSelectComboBox extends MultiSelectComboBox<Currency> implements LocaleChangeObserver {

    public CurrencyMultiSelectComboBox(Collection<Currency> currencies) {
        setItems(currencies);
    }

    public CurrencyMultiSelectComboBox(ExchangeRatesService exchangeRatesService) {
        this(exchangeRatesService.getSupportedCurrencies());
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        Locale newLocale = event.getLocale();
        setItemLabelGenerator(currency -> currency.getDisplayName(newLocale));
        I18nUtil.setHelperText(this, event, "currency-multi-select-combo-box.helper-text");
    }
}
