// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.currency;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.ComboBoxBase;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import jakarta.annotation.Nonnull;

public final class CurrencyUIComponents {

    private CurrencyUIComponents() {}

    public static ComboBox<Currency> comboBox(@Nonnull CurrencyService service) {
        return configureComboBox(new ComboBox<>(), service);
    }

    public static MultiSelectComboBox<Currency> multiSelectComboBox(@Nonnull CurrencyService service) {
        return configureComboBox(new MultiSelectComboBox<>(), service);
    }

    private static <B extends ComboBoxBase<B, Currency, ?>> B configureComboBox(
            @Nonnull B box,
            @Nonnull CurrencyService service) {
        box.setItems(service.getAvailableCurrencies());
        box.setItemLabelGenerator(Currency::name);

        return box;
    }
}
