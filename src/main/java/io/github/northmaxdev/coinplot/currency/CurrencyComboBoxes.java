// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.currency;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.ComboBoxBase;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import jakarta.annotation.Nonnull;

import java.util.Collection;

import static java.util.Comparator.comparing;

public final class CurrencyComboBoxes {

    private CurrencyComboBoxes() {}

    public static ComboBox<Currency> singleAvailable(@Nonnull CurrencyService service) throws Exception {
        return configureAvailable(new ComboBox<>(), service);
    }

    public static ComboBox<Currency> singleUnavailable() {
        return configureUnavailable(new ComboBox<>());
    }

    public static MultiSelectComboBox<Currency> multiAvailable(@Nonnull CurrencyService service) throws Exception {
        return configureAvailable(new MultiSelectComboBox<>(), service);
    }

    public static MultiSelectComboBox<Currency> multiUnavailable() {
        return configureUnavailable(new MultiSelectComboBox<>());
    }

    private static <B extends ComboBoxBase<B, Currency, ?>> B configureAvailable(
            @Nonnull B box,
            @Nonnull CurrencyService service) throws Exception {
        Collection<Currency> sortedItems = service.getAvailableCurrencies()
                .stream()
                // Note: CASE_INSENSITIVE_ORDER does not take locale into account as per its own documentation
                .sorted(comparing(Currency::name, String.CASE_INSENSITIVE_ORDER))
                .toList();

        box.setItems(sortedItems);
        box.setItemLabelGenerator(Currency::name);
        return box;
    }

    private static <B extends ComboBoxBase<B, Currency, ?>> B configureUnavailable(@Nonnull B box) {
        box.setEnabled(false);
        return box;
    }
}
