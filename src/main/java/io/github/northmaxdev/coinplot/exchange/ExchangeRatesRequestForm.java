// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.exchange;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.common.chrono.LocalDateRange;
import io.github.northmaxdev.coinplot.common.fn.ExceptionHandler;
import io.github.northmaxdev.coinplot.common.fn.TriConsumer;
import io.github.northmaxdev.coinplot.currency.Currency;
import io.github.northmaxdev.coinplot.currency.CurrencyComboBox;
import io.github.northmaxdev.coinplot.currency.CurrencyService;
import io.github.northmaxdev.coinplot.currency.MultiCurrencyComboBox;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Locale;
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

        LocalDate today = LocalDate.now(); // Note: keep in mind possible timezone-related issues with ::now()

        this.startPicker = new DatePicker(getStartDateFieldLabelTranslation());
        this.startPicker.setRequired(true);
        this.startPicker.setMax(today);

        this.endPicker = new DatePicker(getEndDateFieldLabelTranslation());
        this.endPicker.setRequired(true);
        this.endPicker.setMax(today);

        this.okButton = new Button(getOKButtonTextTranslation());
        this.okButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

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

        this.okButton.addClickListener(event -> {
            // This should be safe as long as the button is only clicked through the UI (i.e. no programmatic access)
            Currency base = this.baseSelector.getValue();
            Collection<Currency> targets = this.targetSelector.getValue();
            LocalDateRange dateRange = new LocalDateRange(this.startPicker.getValue(), this.endPicker.getValue());

            if (onOKButtonClick != null) {
                onOKButtonClick.accept(base, targets, dateRange);
            }
        });

        this.clearButton.addClickListener(event -> clear());

        toggleOKButtonToInput(null);
    }

    public void fetchCurrencies(@Nonnull CurrencyService service) throws Exception {
        this.baseSelector.fetchItems(service);
        this.targetSelector.fetchItems(service);
    }

    public void fetchCurrencies(@Nonnull CurrencyService service, @Nonnull ExceptionHandler exceptionHandler) {
        this.baseSelector.fetchItems(service, exceptionHandler);
        this.targetSelector.fetchItems(service, exceptionHandler);
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        Locale newLocale = event.getLocale();

        this.baseSelector.setLabel(getBaseFieldLabelTranslation());
        this.targetSelector.setLabel(getTargetFieldLabelTranslation());

        this.startPicker.setLabel(getStartDateFieldLabelTranslation());
        this.startPicker.setLocale(newLocale);
        this.endPicker.setLabel(getEndDateFieldLabelTranslation());
        this.endPicker.setLocale(newLocale);

        this.okButton.setText(getOKButtonTextTranslation());
        this.clearButton.setText(getClearButtonTextTranslation());
    }

    public void clear() {
        this.baseSelector.clear();
        this.targetSelector.clear();
        this.startPicker.clear();
        this.endPicker.clear();
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
