// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.core.currency;

import com.vaadin.flow.component.ItemLabelGenerator;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;

final class CurrencyComboBoxes { // Package-private

    // TODO: ItemFilter<Currency> that supports filtering by both the name OR the ISO code
    public static final ItemLabelGenerator<Currency> DEFAULT_CURRENCY_LABEL_GENERATOR = Currency::getName;

    private CurrencyComboBoxes() {
        throw new UnsupportedOperationException();
    }
}
