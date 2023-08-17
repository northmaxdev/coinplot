// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.core.currency;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.SerializableComparator;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;

import java.util.Comparator;
import java.util.Set;

final class CurrencyComboBoxes { // Package-private

    // TODO: ItemFilter<Currency> that supports filtering by both the name OR the ISO code

    public static final ItemLabelGenerator<Currency> CURRENCY_LABEL_GENERATOR = Currency::getName;
    public static final boolean ALLOW_CUSTOM_VALUE = false;
    // See SerializableComparator class JavaDoc for more information
    public static final SerializableComparator<Currency> COMPARATOR =
            Comparator.comparing(Currency::getCode)::compare;
    public static final String FAILED_FETCH_ERROR_MESSAGE_KEY = "currency-combo-boxes.error-message.failed-fetch";

    private CurrencyComboBoxes() {
        throw new UnsupportedOperationException();
    }

    public static ListDataProvider<Currency> createDataProvider(Set<Currency> currencies) {
        ListDataProvider<Currency> dataProvider = new ListDataProvider<>(currencies);
        dataProvider.setSortComparator(COMPARATOR);
        return dataProvider;
    }
}
