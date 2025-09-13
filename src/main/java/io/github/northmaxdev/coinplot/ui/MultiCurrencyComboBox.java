// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.ui;

import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBoxI18n;

import java.util.Currency;
import java.util.Set;

public final class MultiCurrencyComboBox extends MultiSelectComboBox<Currency> {

    private static final MultiSelectComboBoxI18n I18N_CONFIG = new MultiSelectComboBoxI18n()
            .setRequiredErrorMessage("Must select at least one currency from the list");

    public MultiCurrencyComboBox(String label, Set<Currency> currencies) {
        super(label, currencies);
        setItemLabelGenerator(CurrencyComboBox.CURRENCY_LABEL_GENERATOR);
        setI18n(I18N_CONFIG);
    }
}
