// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.core.currency;

import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import jakarta.annotation.Nonnull;

public final class CurrencyMultiComboBox extends MultiSelectComboBox<Currency> implements LocaleChangeObserver {

    private static final String HELPER_TEXT_KEY = "currency-multi-combo-box.helper-text";

    private final CurrencyService dataSource;

    public CurrencyMultiComboBox(@Nonnull CurrencyService dataSource) {
        this.dataSource = dataSource;

        setItemLabelGenerator(CurrencyComboBoxes.CURRENCY_LABEL_GENERATOR);
        setAllowCustomValue(CurrencyComboBoxes.ALLOW_CUSTOM_VALUE);

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
