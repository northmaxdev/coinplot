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
        var box = new ComboBox<Currency>(label);
        return configureComboBox(box, "Select a currency");
    }

    public MultiSelectComboBox<Currency> createMultiSelectComboBox(@Nullable String label) {
        var box = new MultiSelectComboBox<Currency>(label);
        return configureComboBox(box, "Select currencies");
    }

    // Common configuration for all types of currency combo boxes
    private <B extends ComboBoxBase<B, Currency, ?>> B configureComboBox(
            @Nonnull B box,
            @Nullable String placeholder) {
        // FIXME: The combo boxes do not extend horizontally automatically if the placeholder text is too long
        box.setItems(service.getAvailableCurrencies());
        box.setItemLabelGenerator(Currency::getName);
        box.setAutoOpen(true);
        box.setPlaceholder(placeholder); // TODO: i18n
        return box;
    }
}
