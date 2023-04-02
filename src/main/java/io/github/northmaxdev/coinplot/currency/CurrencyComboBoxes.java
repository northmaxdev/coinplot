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

    public static ComboBox<Currency> singleWithData(@Nonnull CurrencyService service) throws Exception {
        return configureWithData(new ComboBox<>(), service);
    }

    public static ComboBox<Currency> singleWithoutData() {
        return configureWithoutData(new ComboBox<>());
    }

    public static MultiSelectComboBox<Currency> multiWithData(@Nonnull CurrencyService service) throws Exception {
        return configureWithData(new MultiSelectComboBox<>(), service);
    }

    public static MultiSelectComboBox<Currency> multiWithoutData() {
        return configureWithoutData(new MultiSelectComboBox<>());
    }

    private static <B extends ComboBoxBase<B, Currency, ?>> B configureWithData(
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

    private static <B extends ComboBoxBase<B, Currency, ?>> B configureWithoutData(@Nonnull B box) {
        box.setEnabled(false);
        String placeholder = box.getTranslation("currency-combobox.placeholder-on-unavailable");
        box.setPlaceholder(placeholder);
        return box;
    }
}
