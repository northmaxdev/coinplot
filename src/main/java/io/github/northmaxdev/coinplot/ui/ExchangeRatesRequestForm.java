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
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoIcon;
import io.github.northmaxdev.coinplot.domain.DatedExchangeZip;
import io.github.northmaxdev.coinplot.domain.ExchangeRatesService;
import io.github.northmaxdev.coinplot.langext.LocalDateInterval;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

// TODO: Add duration preview to form via LocalDateInterval::toPeriod
public final class ExchangeRatesRequestForm extends FormLayout {

    private static final List<ResponsiveStep> RESPONSIVE_STEPS = List.of(
            new ResponsiveStep("0", 1),
            new ResponsiveStep("350px", 2)
    );
    private static final int CURRENCY_PICKER_COLSPAN = 2;
    // TODO: Sort currencies by display name (right now it's not sorted at all). Refer to ListDataProvider or ask an LLM.
    // TODO: Add flag emoji or SVG to currency label generator
    private static final ItemLabelGenerator<Currency> CURRENCY_LABEL_GENERATOR = Currency::getDisplayName;

    private final ComboBox<Currency> basePicker;
    private final MultiSelectComboBox<Currency> targetPicker;
    private final DatePicker startDatePicker;
    private final DatePicker endDatePicker;
    private @Nullable Consumer<DatedExchangeZip> onSubmit;

    public ExchangeRatesRequestForm(ExchangeRatesService currencyDataSource) {

        //------------------//
        // Currency pickers //
        //------------------//

        // TODO: load async
        // TODO: Handle fetch exceptions
        Set<Currency> supportedCurrencies = currencyDataSource.getSupportedCurrencies();

        basePicker = new ComboBox<>("Base currency", supportedCurrencies);
        basePicker.setRequired(true);
        basePicker.setItemLabelGenerator(CURRENCY_LABEL_GENERATOR);
        setColspan(basePicker, CURRENCY_PICKER_COLSPAN);

        targetPicker = new MultiSelectComboBox<>("Target currencies", supportedCurrencies);
        targetPicker.setRequired(true);
        targetPicker.setItemLabelGenerator(CURRENCY_LABEL_GENERATOR);
        targetPicker.setHelperText("You can select multiple currencies");
        setColspan(targetPicker, CURRENCY_PICKER_COLSPAN);

        //--------------//
        // Date pickers //
        //--------------//

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        startDatePicker = new DatePicker("Start date");
        startDatePicker.setRequired(true);
        startDatePicker.setMax(yesterday);

        endDatePicker = new DatePicker("End date");
        endDatePicker.setRequired(true);
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

        // Eyeballed value - might look different depending on display resolution and/or scaling
        setMinWidth(210, Unit.PIXELS);
    }

    public void setOnSubmit(@Nullable Consumer<DatedExchangeZip> onSubmit) {
        this.onSubmit = onSubmit;
    }

    public void submit() {
        Optional<Currency> base = basePicker.getOptionalValue();
        if (base.isEmpty()) {
            // TODO: Give visual feedback to the user about this field being mandatory
            return;
        }

        Set<Currency> targets = targetPicker.getSelectedItems();
        if (targets.isEmpty()) {
            // TODO: Give visual feedback to the user about this field being mandatory
            return;
        }

        Optional<LocalDate> start = startDatePicker.getOptionalValue();
        if (start.isEmpty()) {
            // TODO: Give visual feedback to the user about this field being mandatory
            return;
        }

        Optional<LocalDate> end = endDatePicker.getOptionalValue();
        if (end.isEmpty()) {
            // TODO: Give visual feedback to the user about this field being mandatory
            return;
        }

        LocalDateInterval dateInterval = new LocalDateInterval(start.get(), end.get());
        DatedExchangeZip exchangeBatch = new DatedExchangeZip(base.get(), targets, dateInterval);
        if (onSubmit != null) {
            onSubmit.accept(exchangeBatch);
        }
    }

    public void clear() {
        basePicker.clear();
        targetPicker.clear();
        startDatePicker.clear();
        endDatePicker.clear();
    }
}
