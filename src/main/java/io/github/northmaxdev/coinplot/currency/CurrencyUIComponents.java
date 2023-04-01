// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.currency;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.ComboBoxBase;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Collection;

import static java.util.Comparator.comparing;

public final class CurrencyUIComponents {

    private CurrencyUIComponents() {}

    public static ComboBox<Currency> comboBox(
            @Nullable String label,
            @Nonnull CurrencyService service) {
        return configureComboBox(new ComboBox<>(label), service);
    }

    public static MultiSelectComboBox<Currency> multiSelectComboBox(
            @Nullable String label,
            @Nonnull CurrencyService service) {
        return configureComboBox(new MultiSelectComboBox<>(label), service);
    }

    private static <B extends ComboBoxBase<B, Currency, ?>> B configureComboBox(
            @Nonnull B box,
            @Nonnull CurrencyService service) {
        Collection<Currency> sortedItems = service.getAvailableCurrencies()
                .stream()
                // Note: CASE_INSENSITIVE_ORDER does not take locale into account as per its own documentation
                .sorted(comparing(Currency::name, String.CASE_INSENSITIVE_ORDER))
                .toList();

        box.setItems(sortedItems);
        box.setItemLabelGenerator(Currency::name);

        return box;
    }
}
