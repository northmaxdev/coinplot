// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.currency;

import com.vaadin.flow.component.combobox.MultiSelectComboBox;

import java.util.Set;

public final class MultiCurrencyComboBox extends
        AbstractCurrencyComboBox<MultiSelectComboBox<Currency>, Set<Currency>> {

    public MultiCurrencyComboBox() {
        super(new MultiSelectComboBox<>());
    }
}
