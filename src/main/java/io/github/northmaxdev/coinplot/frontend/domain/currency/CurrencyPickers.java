// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.currency;

import com.vaadin.flow.component.combobox.ComboBoxBase;
import com.vaadin.flow.data.provider.ListDataProvider;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import io.github.northmaxdev.coinplot.frontend.common.ListDataProviders;
import jakarta.annotation.Nonnull;

import java.util.Comparator;
import java.util.Set;

final class CurrencyPickers { // Package-private

    private static final Comparator<Currency> SORT_COMPARATOR = Comparator.comparing(Currency::getCode);
    // TODO: ItemFilter<Currency> that supports filtering by both the name OR the ISO code

    private CurrencyPickers() {
        throw new UnsupportedOperationException();
    }

    static <C extends ComboBoxBase<C, Currency, ?>> void configure(@Nonnull C component) {
        // Don't null-check: this is a package-private class
        component.setItemLabelGenerator(Currency::getName);
        component.setAllowCustomValue(false);
    }

    // TODO: How exactly do we want to react to "no data" cases? And do we really need this boolean return value for that?
    @SuppressWarnings("UnusedReturnValue") // Sometimes we may not care about the number of loaded items
    static <C extends ComboBoxBase<C, Currency, ?>> boolean loadItems(@Nonnull C component, @Nonnull CurrencyService dataSource) {
        // Don't null-check: this is a package-private class
        Set<Currency> availableCurrencies = dataSource.getAvailableCurrencies();

        ListDataProvider<Currency> dataProvider = ListDataProviders.create(SORT_COMPARATOR, availableCurrencies);
        component.setItems(dataProvider);

        // Return 'true' if we loaded at least one item and 'false' if none at all.
        // This can help components adapt their visual appearance for "no data" cases.
        return !availableCurrencies.isEmpty();
    }
}
