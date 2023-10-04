// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.currency;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import jakarta.annotation.Nonnull;

import java.util.Objects;

public final class SingleCurrencyPicker extends ComboBox<Currency> implements LocaleChangeObserver {

    private static final String HELPER_TEXT_KEY = "single-currency-picker.helper-text";

    private final CurrencyService dataSource;

    public SingleCurrencyPicker(@Nonnull CurrencyService dataSource) {
        this.dataSource = Objects.requireNonNull(dataSource);
        CurrencyPickers.configure(this);

        reloadAvailableCurrencies(); // Initial load
    }

    public void reloadAvailableCurrencies() {
        CurrencyPickers.loadItems(this, dataSource);
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        setItemLabelGenerator(currency -> currency.getDisplayName(event.getLocale()));
        I18NUtilities.setHelperText(this, event, HELPER_TEXT_KEY);
    }
}
