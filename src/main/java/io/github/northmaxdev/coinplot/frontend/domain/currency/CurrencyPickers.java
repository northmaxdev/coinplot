// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.currency;

import com.vaadin.flow.component.combobox.ComboBoxBase;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.SerializableComparator;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import jakarta.annotation.Nonnull;

import java.util.Comparator;
import java.util.Set;

final class CurrencyPickers { // Package-private

    // See SerializableComparator class JavaDoc for useful information
    private static final SerializableComparator<Currency> SORT_COMPARATOR = Comparator.comparing(Currency::getCode)::compare;

    private CurrencyPickers() {
        throw new UnsupportedOperationException();
    }

    static <C extends ComboBoxBase<C, Currency, ?>> void configure(@Nonnull C component) {
        // Explicit null-checks are omitted for an implementation helper method
        component.setItemLabelGenerator(Currency::getName);
        component.setAllowCustomValue(false);
    }

    static <C extends ComboBoxBase<C, Currency, ?>> void loadItems(@Nonnull C component, @Nonnull CurrencyService dataSource) {
        // Explicit null-checks are omitted for an implementation helper method
        Set<Currency> availableCurrencies = dataSource.getAvailableCurrencies();
        Set<Currency> protectiveCopy = Set.copyOf(availableCurrencies);

        ListDataProvider<Currency> dataProvider = new ListDataProvider<>(protectiveCopy);
        dataProvider.setSortComparator(SORT_COMPARATOR);
        // Feature consideration:
        // ItemFilter<Currency> that supports filtering by both the name or the ISO code.
        // See setItems(ItemFilter<T>, ListDataProvider<T>).
        component.setItems(dataProvider);

        component.setEnabled(!protectiveCopy.isEmpty());
    }
}
