// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.currency;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.ComboBoxBase;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public final class CurrencyUIComponents {

    private CurrencyUIComponents() {}

    public static ComboBox<Currency> comboBox(@Nonnull CurrencyService service) {
        return configureComboBox(new ComboBox<>(), service, "Select a currency");
    }

    public static MultiSelectComboBox<Currency> multiSelectComboBox(@Nonnull CurrencyService service) {
        return configureComboBox(new MultiSelectComboBox<>(), service, "Select currencies");
    }

    private static <B extends ComboBoxBase<B, Currency, ?>> B configureComboBox(
            @Nonnull B box,
            @Nonnull CurrencyService service,
            @Nullable String placeholder) {
        box.setItems(service.getAvailableCurrencies());
        box.setItemLabelGenerator(Currency::name);
        box.setPlaceholder(placeholder); // TODO: i18n

        return box;
    }
}
