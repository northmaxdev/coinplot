// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.ui;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.combobox.ComboBox;

import java.util.Currency;
import java.util.Set;

public final class CurrencyComboBox extends ComboBox<Currency> {

    // TODO:
    //  Sort currencies by display name (right now it's not sorted at all). Refer to ListDataProvider or ask an LLM.
    //  Also add to MultiCurrencyComboBox.
    // TODO: Add flag emoji to label generator

    // Some are package-private so MultiCurrencyComboBox can access them too
    static final ItemLabelGenerator<Currency> CURRENCY_LABEL_GENERATOR = Currency::getDisplayName;
    private static final ComboBoxI18n I18N_CONFIG = new ComboBoxI18n()
            .setRequiredErrorMessage("Must select a currency from the list");

    public CurrencyComboBox(String label, Set<Currency> currencies) {
        super(label, currencies);
        setItemLabelGenerator(CURRENCY_LABEL_GENERATOR);
        setI18n(I18N_CONFIG);
    }
}
