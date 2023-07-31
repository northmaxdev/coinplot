// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.core.currency;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.icon.IconFactory;
import com.vaadin.flow.component.icon.VaadinIcon;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;

final class CurrencyComboBoxes { // Package-private

    // TODO: ItemFilter<Currency> that supports filtering by both the name OR the ISO code
    public static final ItemLabelGenerator<Currency> DEFAULT_CURRENCY_LABEL_GENERATOR = Currency::getName;
    public static final IconFactory DEFAULT_ICON_FACTORY = VaadinIcon.DOLLAR;

    private CurrencyComboBoxes() {
        throw new UnsupportedOperationException();
    }
}
