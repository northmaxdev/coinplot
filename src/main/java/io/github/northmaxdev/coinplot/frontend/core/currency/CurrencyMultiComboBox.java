// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.core.currency;

import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;

public final class CurrencyMultiComboBox extends MultiSelectComboBox<Currency> {

    public CurrencyMultiComboBox() {
        setItemLabelGenerator(CurrencyComboBoxes.DEFAULT_CURRENCY_LABEL_GENERATOR);
        // FIXME:
        //  Why can't we set a prefix component for MultiSelectComboBox?
        //  Implementing HasPrefix does not help either.
        setAllowCustomValue(false);
    }
}