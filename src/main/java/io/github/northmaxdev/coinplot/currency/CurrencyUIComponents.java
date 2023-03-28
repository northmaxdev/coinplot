// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.currency;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.ComboBoxBase;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public final class CurrencyUIComponents {

    public static ComboBox<Currency> comboBox(
            @Nullable String label,
            @Nonnull CurrencyService service) {
        return configureComboBox(new ComboBox<>(), service, label, "Select a currency");
    }

    public static MultiSelectComboBox<Currency> multiSelectComboBox(
            @Nullable String label,
            @Nonnull CurrencyService service) {
        return configureComboBox(new MultiSelectComboBox<>(), service, label, "Select currencies");
    }

    private static <B extends ComboBoxBase<B, Currency, ?>> B configureComboBox(
            @Nonnull B box,
            @Nonnull CurrencyService service,
            @Nullable String label,
            @Nullable String placeholder) {
        box.setItems(service.getAvailableCurrencies());
        box.setItemLabelGenerator(Currency::name);
        box.setAutoOpen(true);

        // FIXME: The combo boxes do not extend horizontally automatically if the placeholder text is too long
        box.setLabel(label); // TODO: i18n
        box.setPlaceholder(placeholder); // TODO: i18n

        return box;
    }
}
