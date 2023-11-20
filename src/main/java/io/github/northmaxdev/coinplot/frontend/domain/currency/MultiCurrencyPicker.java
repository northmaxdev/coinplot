// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.currency;

import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import io.github.northmaxdev.coinplot.frontend.I18NUtilities;
import jakarta.annotation.Nonnull;

import java.util.Objects;

public final class MultiCurrencyPicker extends MultiSelectComboBox<Currency> implements LocaleChangeObserver {

    private static final String HELPER_TEXT_KEY = "multi-currency-picker.helper-text";

    private final CurrencyService dataSource;

    public MultiCurrencyPicker(@Nonnull CurrencyService dataSource) {
        this.dataSource = Objects.requireNonNull(dataSource);
        CurrencyPickers.configure(this);

        CurrencyPickers.loadItems(this, dataSource); // Initial load
    }

    public void reloadAvailableCurrencies() {
        CurrencyPickers.loadItems(this, dataSource);
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        I18NUtilities.setHelperText(this, event, HELPER_TEXT_KEY);
    }
}
