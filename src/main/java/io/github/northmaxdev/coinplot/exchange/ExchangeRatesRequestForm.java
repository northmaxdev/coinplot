// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.exchange;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.common.core.LocalDateRange;
import io.github.northmaxdev.coinplot.common.fn.TriConsumer;
import io.github.northmaxdev.coinplot.currency.Currency;
import io.github.northmaxdev.coinplot.currency.CurrencyComboBox;
import io.github.northmaxdev.coinplot.currency.MultiCurrencyComboBox;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public final class ExchangeRatesRequestForm extends FormLayout implements LocaleChangeObserver {

    private final CurrencyComboBox baseSelector;
    private final MultiCurrencyComboBox targetSelector;
    private final DatePicker startPicker;
    private final DatePicker endPicker;
    private final Button okButton;
    private final Button clearButton;

    public ExchangeRatesRequestForm(
            @Nullable TriConsumer<Currency, Collection<Currency>, LocalDateRange> onOKButtonClick) {

        //////////////////////////////
        // Component Initialization //
        //////////////////////////////

        this.baseSelector = new CurrencyComboBox();
        this.baseSelector.setLabel(getBaseFieldLabelTranslation());
        this.baseSelector.setRequired(true);

        this.targetSelector = new MultiCurrencyComboBox();
        this.targetSelector.setLabel(getTargetFieldLabelTranslation());
        this.targetSelector.setRequired(true);

        this.startPicker = new DatePicker(getStartDateFieldLabelTranslation());
        this.startPicker.setRequired(true);

        this.endPicker = new DatePicker(getEndDateFieldLabelTranslation());
        this.endPicker.setRequired(true);
        this.endPicker.setMax(LocalDate.now());

        this.okButton = new Button(getOKButtonTextTranslation());
        this.okButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        // WORKAROUND: Disable the button manually until an input field triggers the toggle listener registered below
        this.okButton.setEnabled(false);

        this.clearButton = new Button(getClearButtonTextTranslation());
        this.clearButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        add(this.baseSelector.getComponent(), this.targetSelector.getComponent());
        add(this.startPicker, this.endPicker, this.okButton, this.clearButton);

        ///////////////
        // Listeners //
        ///////////////

        this.baseSelector.addValueChangeListener(this::toggleOKButtonToInput);
        this.targetSelector.addValueChangeListener(this::toggleOKButtonToInput);
        this.startPicker.addValueChangeListener(this::toggleOKButtonToInput);
        this.endPicker.addValueChangeListener(this::toggleOKButtonToInput);

        // FIXME: It is possible to circumvent this date min/max validation measure by entering an invalid date via
        //  textual input. This is solvable by: (a) disabling textual input or (b) making the OK button toggle listener
        //  check not only whether the form is filled, but also whether its values are sane (actually, both options are
        //  not mutually exclusive)

        // TODO: Disable values later than today

        // After selecting a start date, set the earliest possible end date to be start date plus 1 day.
        // (when the start date selection is cleared, reset this restriction)
        this.startPicker.addValueChangeListener(event -> {
            @Nullable LocalDate newSelection = event.getValue();
            this.endPicker.setMin(newSelection == null ? null : newSelection.plusDays(1));
        });

        // After selecting an end date, set the latest possible start date to be end date minus 1 day.
        // (when the end date selection is cleared, reset this restriction)
        this.endPicker.addValueChangeListener(event -> {
            @Nullable LocalDate newSelection = event.getValue();
            this.startPicker.setMax(newSelection == null ? null : newSelection.minusDays(1));
        });

        this.okButton.addClickListener(event -> {
            // Note: all these getters technically return nullable values, but we *assume* they're all non-null and sane
            // by the time we get to this block of code as we rely on our surface-level filters of invalid input, such
            // as keeping the OK button disabled while the form is not fully filled and/or invalid. If the user somehow
            // circumvents these validation measures, bad input might get through, and it ain't going to be fun.
            Currency base = this.baseSelector.getValue();
            Collection<Currency> targets = this.targetSelector.getValue();
            LocalDateRange dateRange = new LocalDateRange(this.startPicker.getValue(), this.endPicker.getValue());

            if (onOKButtonClick != null) {
                onOKButtonClick.accept(base, targets, dateRange);
            }
        });

        this.clearButton.addClickListener(event -> clear());
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        baseSelector.setLabel(getBaseFieldLabelTranslation());
        targetSelector.setLabel(getTargetFieldLabelTranslation());
        startPicker.setLabel(getStartDateFieldLabelTranslation());
        endPicker.setLabel(getEndDateFieldLabelTranslation());
        okButton.setText(getOKButtonTextTranslation());
        clearButton.setText(getClearButtonTextTranslation());
    }

    public void clear() {
        baseSelector.clear();
        targetSelector.clear();
        startPicker.clear();
        endPicker.clear();
    }

    // The parameter is there so this method can be referenced as a ValueChangeEvent listener
    private void toggleOKButtonToInput(HasValue.ValueChangeEvent<?> ignored) {
        Optional<Currency> selectedBase = this.baseSelector.getOptionalValue();
        Collection<Currency> selectedTargets = this.targetSelector.getValue();
        @Nullable LocalDate selectedStartDate = this.startPicker.getValue();
        @Nullable LocalDate selectedEndDate = this.endPicker.getValue();

        boolean inputIsValid = selectedBase.isPresent()
                && !selectedTargets.isEmpty()
                && LocalDateRange.isValid(selectedStartDate, selectedEndDate);

        this.okButton.setEnabled(inputIsValid);
    }

    //////////
    // i18n //
    //////////

    private String getBaseFieldLabelTranslation() {
        return getTranslation("form.input.base-selection.label");
    }

    private String getTargetFieldLabelTranslation() {
        return getTranslation("form.input.target-selection.label");
    }

    private String getStartDateFieldLabelTranslation() {
        return getTranslation("form.input.start-date-selection.label");
    }

    private String getEndDateFieldLabelTranslation() {
        return getTranslation("form.input.end-date-selection.label");
    }

    private String getOKButtonTextTranslation() {
        return getTranslation("form.button.ok");
    }

    private String getClearButtonTextTranslation() {
        return getTranslation("form.button.clear");
    }
}
