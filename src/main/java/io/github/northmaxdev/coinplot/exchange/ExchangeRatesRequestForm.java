// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.exchange;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import io.github.northmaxdev.coinplot.common.core.LocalDateRange;
import io.github.northmaxdev.coinplot.currency.Currency;
import io.github.northmaxdev.coinplot.currency.CurrencyService;
import io.github.northmaxdev.coinplot.currency.CurrencyUIComponents;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

// TODO: i18n of all UI strings
public final class ExchangeRatesRequestForm extends FormLayout {

    @FunctionalInterface
    public interface FormInputConsumer {

        void consume(@Nonnull Currency base, @Nonnull Collection<Currency> targets, @Nonnull LocalDateRange dateRange);
    }

    private final ComboBox<Currency> baseSelector;
    private final MultiSelectComboBox<Currency> targetSelector;
    private final DatePicker startPicker;
    private final DatePicker endPicker;
    private final Button okButton;

    public ExchangeRatesRequestForm(
            @Nonnull CurrencyService currencyService,
            @Nonnull FormInputConsumer onOKButtonClick) {

        //////////////////////////////
        // Component Initialization //
        //////////////////////////////

        this.baseSelector = CurrencyUIComponents.comboBox("Base currency", currencyService);
        baseSelector.setRequired(true);

        this.targetSelector = CurrencyUIComponents.multiSelectComboBox("Target currencies", currencyService);
        targetSelector.setRequired(true);

        this.startPicker = new DatePicker("Start date");
        startPicker.setRequired(true);

        this.endPicker = new DatePicker("End date");
        endPicker.setRequired(true);

        this.okButton = new Button("OK");
        okButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        // WORKAROUND: Disable the button manually until an input field triggers the toggle listener registered below
        okButton.setEnabled(false);

        Button clearButton = new Button("Clear");
        clearButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        ///////////////
        // Listeners //
        ///////////////

        baseSelector.addValueChangeListener(this::toggleOKButtonToInput);
        targetSelector.addValueChangeListener(this::toggleOKButtonToInput);
        startPicker.addValueChangeListener(this::toggleOKButtonToInput);
        endPicker.addValueChangeListener(this::toggleOKButtonToInput);

        // FIXME: It is possible to circumvent this date min/max validation measure by entering an invalid date via
        //  textual input. This is solvable by: (a) disabling textual input or (b) making the OK button toggle listener
        //  check not only whether the form is filled, but also whether its values are sane (actually, both options are
        //  not mutually exclusive)

        // After selecting a start date, set the earliest possible end date to be start date plus 1 day.
        // (when the start date selection is cleared, reset this end date picker restriction as well)
        startPicker.addValueChangeListener(event -> {
            @Nullable LocalDate newSelection = event.getValue();
            endPicker.setMin(newSelection == null ? null : newSelection.plusDays(1));
        });

        // After selecting an end date, set the latest possible start date to be end date minus 1 day.
        // (when the end date selection is cleared, reset this start date picker restriction as well)
        endPicker.addValueChangeListener(event -> {
            @Nullable LocalDate newSelection = event.getValue();
            startPicker.setMax(newSelection == null ? null : newSelection.minusDays(1));
        });

        okButton.addClickListener(event -> {
            // Note: all these getters technically return nullable values, but we *assume* they're all non-null and sane
            // by the time we get to this block of code as we rely on our surface-level filters of invalid input, such
            // as keeping the OK button disabled while the form is not fully filled and/or invalid. If the user somehow
            // circumvents these validation measures, bad input might get through, and it ain't going to be fun.
            Currency base = baseSelector.getValue();
            Collection<Currency> targets = targetSelector.getSelectedItems();
            LocalDateRange dateRange = new LocalDateRange(startPicker.getValue(), endPicker.getValue());

            onOKButtonClick.consume(base, targets, dateRange);
        });

        clearButton.addClickListener(event -> clear());

        add(baseSelector, targetSelector, startPicker, endPicker, okButton, clearButton);
    }

    public boolean isFilled() {
        Optional<Currency> selectedBase = baseSelector.getOptionalValue();
        Collection<Currency> selectedTargets = targetSelector.getSelectedItems();
        Optional<LocalDate> selectedStart = startPicker.getOptionalValue();
        Optional<LocalDate> selectedEnd = endPicker.getOptionalValue();

        return selectedBase.isPresent()
                && !selectedTargets.isEmpty()
                && selectedStart.isPresent()
                && selectedEnd.isPresent();
    }

    public void clear() {
        baseSelector.clear();
        targetSelector.clear();
        startPicker.clear();
        endPicker.clear();
    }

    // The parameter is there so this method can be referenced as a ValueChangeEvent listener
    private void toggleOKButtonToInput(HasValue.ValueChangeEvent<?> ignored) {
        okButton.setEnabled(isFilled());
    }
}
