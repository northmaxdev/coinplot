// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.currency;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.ComboBoxBase;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class CurrencyUIComponentFactory {

    private final CurrencyService service;

    @Autowired
    public CurrencyUIComponentFactory(CurrencyService service) {
        this.service = service;
    }

    public ComboBox<Currency> createComboBox(@Nullable String label) {
        return configureComboBox(new ComboBox<>(), label, "Select a currency");
    }

    public MultiSelectComboBox<Currency> createMultiSelectComboBox(@Nullable String label) {
        return configureComboBox(new MultiSelectComboBox<>(), label, "Select currencies");
    }

    private <B extends ComboBoxBase<B, Currency, ?>> B configureComboBox(
            @Nonnull B box,
            @Nullable String label,
            @Nullable String placeholder) {
        box.setLabel(label); // TODO: i18n
        // FIXME: The combo boxes do not extend horizontally automatically if the placeholder text is too long
        box.setPlaceholder(placeholder); // TODO: i18n

        box.setItems(service.getAvailableCurrencies());
        box.setItemLabelGenerator(Currency::name);

        box.setAutoOpen(true);

        return box;
    }
}
