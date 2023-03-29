// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.currency;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.ComboBoxBase;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

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
        box.setItems(service.getAvailableCurrencies());
        box.setItemLabelGenerator(Currency::name);

        return box;
    }
}
