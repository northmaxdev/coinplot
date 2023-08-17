// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.core.currency;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import jakarta.annotation.Nonnull;

public final class CurrencyComboBox extends ComboBox<Currency> implements LocaleChangeObserver {

    private static final String HELPER_TEXT_KEY = "currency-combo-box.helper-text";

    private final CurrencyService dataSource;

    public CurrencyComboBox(@Nonnull CurrencyService dataSource) {
        this.dataSource = dataSource;
        CurrencyComboBoxes.setBasicConfiguration(this);

        fetchItems();
    }

    public void fetchItems() {
        CurrencyComboBoxes.fetchItemsInto(this, dataSource);
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        I18NUtilities.setHelperText(this, event, HELPER_TEXT_KEY);
        I18NUtilities.setErrorMessage(this, event, CurrencyComboBoxes.FAILED_FETCH_ERROR_MESSAGE_KEY);
    }
}
