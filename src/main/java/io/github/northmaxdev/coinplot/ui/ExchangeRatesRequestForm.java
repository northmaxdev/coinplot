// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.ui;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.IconRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.function.SerializableComparator;
import com.vaadin.flow.theme.lumo.LumoIcon;
import io.github.northmaxdev.coinplot.domain.DatedExchangeZip;
import io.github.northmaxdev.coinplot.domain.ExchangeRatesService;
import io.github.northmaxdev.coinplot.langext.LocalDateInterval;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public final class ExchangeRatesRequestForm extends FormLayout implements Clearable {

    //------------------//
    // Layout constants //
    //------------------//

    private static final List<ResponsiveStep> RESPONSIVE_STEPS = List.of(
            new ResponsiveStep("0", 1),
            new ResponsiveStep("350px", 2)
    );

    private static final int CURRENCY_PICKER_COLSPAN = 2;

    //--------------//
    // UX constants //
    //--------------//

    private static final ItemLabelGenerator<Currency> CURRENCY_LABEL_GENERATOR =
            currency -> currency.getDisplayName() + " (" + currency.getCurrencyCode() + ')';

    private static final Renderer<Currency> CURRENCY_RENDERER = new IconRenderer<>(currency -> {
        Image icon = FlagIcons.create(currency);

        // FlagIcons does not specify or modify the icon size, and SVGs too can have a predefined size,
        // so we should set here an appropriate width & height for the combo-boxes.
        icon.setMaxWidth(2.0f, Unit.EM);
        icon.setMaxHeight(2.0f, Unit.EM);

        return new HorizontalLayout(icon);
    }, CURRENCY_LABEL_GENERATOR);

    // Note: Currency.getDisplayName() could return its ISO 4217 code if a display name is unavailable.
    // Source: the method's JavaDoc. This could make this comparator severely inconsistent.
    private static final SerializableComparator<Currency> CURRENCY_SORT_COMPARATOR =
            (c1, c2) -> c1.getDisplayName().compareTo(c2.getDisplayName());

    //-----------------//
    // Instance fields //
    //-----------------//

    private final ComboBox<Currency> basePicker;
    private final MultiSelectComboBox<Currency> targetPicker;
    private final DatePicker startDatePicker;
    private final DatePicker endDatePicker;
    private @Nullable Consumer<DatedExchangeZip> onSubmit;

    public ExchangeRatesRequestForm(ExchangeRatesService exchangeRatesService) {

        //------------------//
        // Currency pickers //
        //------------------//

        basePicker = new ComboBox<>("Base currency");
        basePicker.setRequired(true);
        basePicker.setErrorMessage("A base currency must be selected");
        basePicker.setItemLabelGenerator(CURRENCY_LABEL_GENERATOR);
        basePicker.setRenderer(CURRENCY_RENDERER);
        setColspan(basePicker, CURRENCY_PICKER_COLSPAN);

        targetPicker = new MultiSelectComboBox<>("Target currencies");
        targetPicker.setRequired(true);
        targetPicker.setErrorMessage("At least one target currency must be selected");
        targetPicker.setItemLabelGenerator(CURRENCY_LABEL_GENERATOR);
        targetPicker.setHelperText("You can select multiple currencies");
        targetPicker.setRenderer(CURRENCY_RENDERER);
        setColspan(targetPicker, CURRENCY_PICKER_COLSPAN);

        try {
            Set<Currency> supportedCurrencies = exchangeRatesService.getSupportedCurrencies();

            ListDataProvider<Currency> currencyPickerItems = new ListDataProvider<>(supportedCurrencies);
            currencyPickerItems.setSortComparator(CURRENCY_SORT_COMPARATOR);

            basePicker.setItems(currencyPickerItems);
            targetPicker.setItems(currencyPickerItems);
        } catch (RuntimeException e) {
            String errorMessage = "Failed to load currency data (" + e.getClass().getName() + ')';

            basePicker.setErrorMessage(errorMessage);
            basePicker.setInvalid(true);

            targetPicker.setErrorMessage(errorMessage);
            targetPicker.setInvalid(true);

            // Disabling the whole form avoids awkward UX when we disable just the currency fields on a bad fetch.
            // Example: submitting with empty date pickers provides no visual feedback to the user because
            // the form first checks if we selected currencies, which we didn't because there are none available.
            // Simply disabling the entire form is probably the cleanest solution to this.
            setEnabled(false);
        }

        //--------------//
        // Date pickers //
        //--------------//

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        startDatePicker = new DatePicker("Start date");
        startDatePicker.setRequired(true);
        startDatePicker.setErrorMessage("A start date must be selected");
        startDatePicker.setMax(yesterday);

        endDatePicker = new DatePicker("End date");
        endDatePicker.setRequired(true);
        endDatePicker.setErrorMessage("An end date must be selected");
        endDatePicker.setMax(today);

        startDatePicker.addValueChangeListener(event -> {
            LocalDate start = event.getValue(); // Nullable (Spring nullability annotations don't support local variables)
            LocalDate minEnd = (start == null) ? null : start.plusDays(1);
            endDatePicker.setMin(minEnd);
        });

        endDatePicker.addValueChangeListener(event -> {
            LocalDate end = event.getValue(); // Nullable (Spring nullability annotations don't support local variables)
            LocalDate maxStart = (end == null) ? yesterday : end.minusDays(1);
            startDatePicker.setMax(maxStart);
        });

        //---------//
        // Buttons //
        //---------//

        Button okButton = new Button("Submit", LumoIcon.CHECKMARK.create());
        okButton.addSingleClickListener(_ -> submit());
        okButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        okButton.addClickShortcut(Key.ENTER);
        okButton.setTooltipText("Keyboard shortcut: \"Enter\"");
        onSubmit = null;

        Button clearButton = new Button("Clear", LumoIcon.CROSS.create());
        clearButton.addSingleClickListener(_ -> clear());
        clearButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        //--------//
        // Layout //
        //--------//

        // https://vaadin.com/docs/latest/components/form-layout#button-placement
        HorizontalLayout buttonBar = new HorizontalLayout(okButton, clearButton);
        buttonBar.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        buttonBar.setPadding(false); // This is so it's horizontally in line with the rest of the form's components
        buttonBar.setMargin(true);

        add(basePicker, targetPicker, startDatePicker, endDatePicker, buttonBar);
        setResponsiveSteps(RESPONSIVE_STEPS);

        setMinWidth(210, Unit.PIXELS); // Eyeballed value - might look different depending on display resolution and/or scaling
    }

    public void setOnSubmit(@Nullable Consumer<DatedExchangeZip> onSubmit) {
        this.onSubmit = onSubmit;
    }

    public void submit() {
        if (basePicker.isEmpty()) {
            basePicker.setInvalid(true);
            return;
        }
        if (targetPicker.isEmpty()) {
            targetPicker.setInvalid(true);
            return;
        }
        if (startDatePicker.isEmpty()) {
            startDatePicker.setInvalid(true);
            return;
        }
        if (endDatePicker.isEmpty()) {
            endDatePicker.setInvalid(true);
            return;
        }

        // We don't explicitly null-check the values, but as long as we prematurely exit the method if a field is empty, this should be safe
        LocalDateInterval dateInterval = new LocalDateInterval(startDatePicker.getValue(), endDatePicker.getValue());
        DatedExchangeZip exchangeZip = new DatedExchangeZip(basePicker.getValue(), targetPicker.getSelectedItems(), dateInterval);
        if (onSubmit != null) {
            onSubmit.accept(exchangeZip);
        }
    }

    @Override
    public void clear() {
        basePicker.clear();
        targetPicker.clear();
        startDatePicker.clear();
        endDatePicker.clear();

        // clear() triggers the fields' "no selection" error messages, but since clearing the form is a deliberate action,
        // it's not right to show error messages for that, so we manually mark the fields as valid.
        basePicker.setInvalid(false);
        targetPicker.setInvalid(false);
        startDatePicker.setInvalid(false);
        endDatePicker.setInvalid(false);
    }
}
