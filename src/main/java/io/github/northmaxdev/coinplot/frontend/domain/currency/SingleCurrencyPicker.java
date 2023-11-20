// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.currency;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import io.github.northmaxdev.coinplot.frontend.I18NUtilities;
import jakarta.annotation.Nonnull;

import java.util.Objects;

public final class SingleCurrencyPicker extends ComboBox<Currency> implements LocaleChangeObserver {

    private final CurrencyService dataSource;

    public SingleCurrencyPicker(@Nonnull CurrencyService dataSource) {
        this.dataSource = Objects.requireNonNull(dataSource);
        CurrencyPickers.configure(this);

        CurrencyPickers.loadItems(this, dataSource); // Initial load
    }

    public void reloadAvailableCurrencies() {
        CurrencyPickers.loadItems(this, dataSource);
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        I18NUtilities.setHelperText(this, event, "single-currency-picker.helper-text");
    }
}
