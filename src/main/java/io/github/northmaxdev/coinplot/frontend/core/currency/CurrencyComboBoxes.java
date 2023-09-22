// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.core.currency;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.combobox.ComboBoxBase;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.SerializableComparator;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import jakarta.annotation.Nonnull;

import java.util.Comparator;
import java.util.Set;

final class CurrencyComboBoxes { // Package-private

    private static final ItemLabelGenerator<Currency> ITEM_LABEL_GENERATOR = Currency::getName;
    // TODO: ItemFilter<Currency> that supports filtering by both the name OR the ISO code
    // See SerializableComparator class JavaDoc for more information
    private static final SerializableComparator<Currency> COMPARATOR =
            Comparator.comparing(Currency::getCode)::compare;

    private CurrencyComboBoxes() {
        throw new UnsupportedOperationException();
    }

    static <C extends ComboBoxBase<C, Currency, ?>> void configure(@Nonnull C component) {
        // Don't null-check: this is a package-private class
        component.setItemLabelGenerator(ITEM_LABEL_GENERATOR);
        component.setAllowCustomValue(false);
    }

    @SuppressWarnings("UnusedReturnValue") // Sometimes we may not care about the number of loaded items
    static <C extends ComboBoxBase<C, Currency, ?>> boolean loadItems(@Nonnull C component, @Nonnull CurrencyService dataSource) {
        // Don't null-check: this is a package-private class
        Set<Currency> availableCurrencies = dataSource.getAvailableCurrencies();

        // There's no guarantee (in terms of API contracts) that CurrencyService::getAvailableCurrencies
        // will return an immutable set, so make a protective copy just in case.
        Set<Currency> copiedItems = Set.copyOf(availableCurrencies);

        ListDataProvider<Currency> dataProvider = new ListDataProvider<>(copiedItems);
        dataProvider.setSortComparator(COMPARATOR);
        component.setItems(dataProvider);

        // Return 'true' if we loaded at least one item and 'false' if none at all.
        // This can help components adapt their visual appearance for "no data" cases.
        return !copiedItems.isEmpty();
    }
}
