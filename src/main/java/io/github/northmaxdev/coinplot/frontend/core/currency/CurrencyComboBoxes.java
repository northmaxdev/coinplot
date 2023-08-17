// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.core.currency;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.combobox.ComboBoxBase;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.SerializableComparator;
import io.github.northmaxdev.coinplot.backend.core.ResourceFetchFailureException;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import jakarta.annotation.Nonnull;

import java.util.Comparator;
import java.util.Set;

final class CurrencyComboBoxes { // Package-private

    // TODO: ItemFilter<Currency> that supports filtering by both the name OR the ISO code

    public static final ItemLabelGenerator<Currency> CURRENCY_LABEL_GENERATOR = Currency::getName;
    public static final boolean ALLOW_CUSTOM_VALUE = false;
    public static final String FAILED_FETCH_ERROR_MESSAGE_KEY = "currency-combo-boxes.error-message.failed-fetch";

    // See SerializableComparator class JavaDoc for more information
    private static final SerializableComparator<Currency> COMPARATOR =
            Comparator.comparing(Currency::getCode)::compare;

    private CurrencyComboBoxes() {
        throw new UnsupportedOperationException();
    }

    public static <C extends ComboBoxBase<C, Currency, ?>> void fetchItemsInto(
            @Nonnull C component,
            @Nonnull CurrencyService dataSource) {
        boolean failed = false;
        try {
            Set<Currency> currencies = dataSource.getAvailableCurrencies();

            ListDataProvider<Currency> dataProvider = new ListDataProvider<>(currencies);
            dataProvider.setSortComparator(COMPARATOR);
            component.setItems(dataProvider);

            // The flag is already "false"
        } catch (ResourceFetchFailureException e) {
            failed = true;
        } finally {
            component.setEnabled(!failed);
            component.setInvalid(failed);
        }
    }
}
