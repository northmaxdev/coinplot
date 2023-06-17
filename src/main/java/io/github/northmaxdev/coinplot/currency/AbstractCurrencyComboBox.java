// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.currency;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.combobox.ComboBoxBase;
import com.vaadin.flow.shared.Registration;
import io.github.northmaxdev.coinplot.common.fn.ExceptionHandler;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Collection;
import java.util.Optional;

import static java.util.Comparator.comparing;

public sealed abstract class AbstractCurrencyComboBox<B extends ComboBoxBase<B, Currency, V>, V>
        permits CurrencyComboBox, MultiCurrencyComboBox {

    // This is done due to ComboBox and MultiSelectComboBox both already extending a common base class, therefore
    // eliminating the option to form a proper inheritance hierarchy.
    private final B comboBox;

    protected AbstractCurrencyComboBox(B comboBox) {
        this.comboBox = comboBox;
        this.comboBox.setItemLabelGenerator(Currency::name);
    }

    public final B getComponent() {
        return comboBox;
    }

    public final void fetchItems(@Nonnull CurrencyService service) throws Exception {
        Collection<Currency> sortedItems = service.getAvailableCurrencies()
                .stream()
                // Note: CASE_INSENSITIVE_ORDER does not take locale into account as per its own documentation
                .sorted(comparing(Currency::name, String.CASE_INSENSITIVE_ORDER))
                .toList();

        comboBox.setItems(sortedItems);
    }

    public final void fetchItems(@Nonnull CurrencyService service, @Nonnull ExceptionHandler exceptionHandler) {
        try {
            fetchItems(service);
        } catch (Exception e) {
            exceptionHandler.handle(e);
        }
    }

    ////////////////////////////////////////
    // Delegation-methods for convenience //
    ////////////////////////////////////////

    public final V getValue() {
        return comboBox.getValue();
    }

    public final Optional<V> getOptionalValue() {
        return comboBox.getOptionalValue();
    }

    public final void clear() {
        comboBox.clear();
    }

    public final void setLabel(@Nullable String label) {
        comboBox.setLabel(label);
    }

    public final void setRequired(boolean required) {
        comboBox.setRequired(required);
    }

    public final Registration addValueChangeListener(
            @Nonnull HasValue.ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<B, V>> listener) {
        return comboBox.addValueChangeListener(listener);
    }
}
