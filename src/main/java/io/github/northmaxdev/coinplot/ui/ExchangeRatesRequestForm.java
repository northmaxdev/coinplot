// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.ui;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoIcon;
import io.github.northmaxdev.coinplot.domain.CurrencyExchangeBatch;
import io.github.northmaxdev.coinplot.domain.ExchangeRatesService;
import io.github.northmaxdev.coinplot.util.LocalDateInterval;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

// TODO: Set minimum width for the entire layout
// TODO: Add duration preview to form via LocalDateInterval::toPeriod
public final class ExchangeRatesRequestForm extends FormLayout {

    private static final List<ResponsiveStep> RESPONSIVE_STEPS = List.of(
            new ResponsiveStep("0", 1),
            new ResponsiveStep("350px", 2)
    );
    private static final int CURRENCY_PICKER_COLSPAN = 2;

    private final CurrencyComboBox basePicker;
    private final MultiCurrencyComboBox targetPicker;
    private final DatePicker startDatePicker;
    private final DatePicker endDatePicker;
    private @Nullable Consumer<CurrencyExchangeBatch> onSubmit;

    public ExchangeRatesRequestForm(ExchangeRatesService currencyDataSource) {

        //------------------//
        // Currency pickers //
        //------------------//

        // TODO: load async
        Set<Currency> supportedCurrencies = currencyDataSource.getSupportedCurrencies();

        basePicker = new CurrencyComboBox("Base currency", supportedCurrencies);
        basePicker.setRequired(true);
        setColspan(basePicker, CURRENCY_PICKER_COLSPAN);

        targetPicker = new MultiCurrencyComboBox("Target currencies", supportedCurrencies);
        targetPicker.setRequired(true);
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
        okButton.setTooltipText("Keyboard hotkey: Enter");
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
    }

    public void setOnSubmit(@Nullable Consumer<CurrencyExchangeBatch> onSubmit) {
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
        CurrencyExchangeBatch exchangeBatch = new CurrencyExchangeBatch(base.get(), targets, dateInterval);
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
