// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.ui.fields;

import com.vaadin.flow.component.combobox.ComboBox;
import io.github.northmaxdev.coinplot.ui.IsraelL10n;

import java.util.Collection;
import java.util.Currency;

public final class CurrencyComboBox extends ComboBox<Currency> {

    public CurrencyComboBox(Collection<Currency> currencies) {
        setItems(currencies);
        setItemLabelGenerator(IsraelL10n.CURRENCY_LABEL_GENERATOR);
        setI18n(IsraelL10n.COMBO_BOX_L10N);
    }
}
