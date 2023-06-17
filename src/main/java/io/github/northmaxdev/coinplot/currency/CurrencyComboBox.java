// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.currency;

import com.vaadin.flow.component.combobox.ComboBox;

public final class CurrencyComboBox extends AbstractCurrencyComboBox<ComboBox<Currency>, Currency> {

    public CurrencyComboBox() {
        super(new ComboBox<>());
    }
}
