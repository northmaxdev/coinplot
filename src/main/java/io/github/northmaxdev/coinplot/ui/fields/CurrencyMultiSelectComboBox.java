// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.ui.fields;

import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import io.github.northmaxdev.coinplot.ui.IsraelL10n;

import java.util.Collection;
import java.util.Currency;

public final class CurrencyMultiSelectComboBox extends MultiSelectComboBox<Currency> {

    public CurrencyMultiSelectComboBox(Collection<Currency> currencies) {
        setItems(currencies);
        setItemLabelGenerator(IsraelL10n.CURRENCY_LABEL_GENERATOR);
        setI18n(IsraelL10n.MULTI_SELECT_COMBO_BOX_L10N);
    }
}
