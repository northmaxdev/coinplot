// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.currency;

import com.vaadin.flow.component.combobox.ComboBoxBase;
import io.github.northmaxdev.coinplot.common.fn.ExceptionHandler;
import jakarta.annotation.Nonnull;

import java.util.Collection;

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

    public final V getValue() {
        return comboBox.getValue();
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
}
