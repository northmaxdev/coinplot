// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.core.currency;

import com.vaadin.flow.component.combobox.ComboBox;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;

public final class CurrencyComboBox extends ComboBox<Currency> {

    public CurrencyComboBox() {
        setItemLabelGenerator(CurrencyComboBoxes.DEFAULT_CURRENCY_LABEL_GENERATOR);
        setPrefixComponent(CurrencyComboBoxes.DEFAULT_ICON_FACTORY.create());
        setAllowCustomValue(false);
    }
}
