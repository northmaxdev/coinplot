// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.ui.panels;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoIcon;
import io.github.northmaxdev.coinplot.domain.currency.CurrencyExchangeBatch;
import io.github.northmaxdev.coinplot.domain.currency.ExchangeRatesService;
import io.github.northmaxdev.coinplot.ui.IsraelL10n;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public final class CurrencyExchangeBatchForm extends FormLayout {

    private static final int CURRENCY_PICKER_COLSPAN = 2;
    private static final List<ResponsiveStep> RESPONSIVE_STEPS = List.of(
            new ResponsiveStep("0", 1),
            new ResponsiveStep("350px", 2) // TODO: Do not use pixel count for width, use something more relative
    );

    private final ComboBox<Currency> basePicker;
    private final MultiSelectComboBox<Currency> targetPicker;
    private final DatePicker startDatePicker;
    private final DatePicker endDatePicker;
    private @Nullable Consumer<CurrencyExchangeBatch> onSubmit;

    public CurrencyExchangeBatchForm(ExchangeRatesService currencyDataSource) {

        //////////////////////
        // Currency pickers //
        //////////////////////

        Set<Currency> supportedCurrencies = currencyDataSource.getSupportedCurrencies();

        this.basePicker = new ComboBox<>("מטבע המקור", supportedCurrencies);
        this.basePicker.setTooltipText("המטבע שאתם נותנים בהמרה");
        this.basePicker.setRequired(true);
        setColspan(this.basePicker, CURRENCY_PICKER_COLSPAN);
        IsraelL10n.localize(this.basePicker);

        this.targetPicker = new MultiSelectComboBox<>("מטבע היעד", supportedCurrencies);
        this.targetPicker.setTooltipText("המטבע שאתם מקבלים בהמרה");
        this.targetPicker.setRequired(true);
        setColspan(this.targetPicker, CURRENCY_PICKER_COLSPAN);
        IsraelL10n.localize(this.targetPicker);

        //////////////////
        // Date pickers //
        //////////////////

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1L);

        // TODO: Set tooltips for date pickers (null does not disable the default ones)

        this.startDatePicker = new DatePicker("מתאריך");
        this.startDatePicker.setRequired(true);
        this.startDatePicker.setMax(yesterday);
        IsraelL10n.localize(this.startDatePicker);

        this.endDatePicker = new DatePicker("עד לתאריך");
        this.endDatePicker.setRequired(true);
        this.endDatePicker.setMax(today);
        IsraelL10n.localize(this.endDatePicker);

        this.startDatePicker.addValueChangeListener(event -> {
            LocalDate start = event.getValue(); // Nullable (Spring nullability annotations don't support local variables)
            LocalDate minEnd = (start == null) ? null : start.plusDays(1L);
            this.endDatePicker.setMin(minEnd);
        });

        this.endDatePicker.addValueChangeListener(event -> {
            LocalDate end = event.getValue(); // Nullable (Spring nullability annotations don't support local variables)
            LocalDate maxStart = (end == null) ? yesterday : end.minusDays(1L);
            this.startDatePicker.setMax(maxStart);
        });

        /////////////
        // Buttons //
        /////////////

        Button okButton = new Button("אישור", LumoIcon.CHECKMARK.create());
//        okButton.addSingleClickListener(_ -> {
//        });
        okButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        okButton.addClickShortcut(Key.ENTER);
        okButton.setTooltipText("ניתן גם ללחוץ \"Enter\" בתור מקש קיצור");
        this.onSubmit = null;

        Button clearButton = new Button("איפוס", LumoIcon.CROSS.create());
        clearButton.addSingleClickListener(_ -> clear());
        clearButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        clearButton.addClickShortcut(Key.DELETE);
        clearButton.setTooltipText("ניתן גם ללחוץ \"Delete\" בתור מקש קיצור");

        ////////////
        // Layout //
        ////////////

        // https://vaadin.com/docs/latest/components/form-layout#button-placement
        HorizontalLayout buttonBar = new HorizontalLayout(okButton, clearButton);
        buttonBar.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        buttonBar.setPadding(false); // This is so it's horizontally in line with the rest of the form's components
        buttonBar.setMargin(true);

        add(this.basePicker, this.targetPicker, this.startDatePicker, this.endDatePicker, buttonBar);
        setResponsiveSteps(RESPONSIVE_STEPS);
    }

    public void submit() {
//        @Nullable Currency baseCurrency = baseCurrencyPicker.getValue();
//        @Nonnull Set<Currency> targetCurrencies = targetCurrencyPicker.getSelectedItems();
//        @Nullable LocalDate startDate = startDatePicker.getValue();
//        @Nullable LocalDate endDate = endDatePicker.getValue();
//
//        if (baseCurrency != null
//                && !targetCurrencies.isEmpty()
//                && startDate != null
//                && endDate != null) {
//            // The date pickers are responsible for ensuring the dates are always
//            // in a valid chronological order, as required by LocalDateInterval.
//            LocalDateInterval dateInterval = new LocalDateInterval(startDate, endDate);
//            ExchangeBatch exchangeBatch = new ExchangeBatch(baseCurrency, targetCurrencies, dateInterval);
//            onSubmit.accept(exchangeBatch);
//        }
    }

    public void setOnSubmit(@Nullable Consumer<CurrencyExchangeBatch> onSubmit) {
        this.onSubmit = onSubmit;
    }

    public void clear() {
        this.basePicker.clear();
        this.targetPicker.clear();
        this.startDatePicker.clear();
        this.endDatePicker.clear();
    }
}
