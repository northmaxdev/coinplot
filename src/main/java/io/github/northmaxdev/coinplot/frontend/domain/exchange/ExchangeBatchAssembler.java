// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeBatch;
import io.github.northmaxdev.coinplot.frontend.domain.currency.MultiCurrencyPicker;
import io.github.northmaxdev.coinplot.frontend.domain.currency.SingleCurrencyPicker;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import io.github.northmaxdev.coinplot.lang.chrono.LocalDateInterval;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public final class ExchangeBatchAssembler extends CustomField<ExchangeBatch> implements LocaleChangeObserver {

    private static final String BASE_PICKER_LABEL_KEY = "exchange-batch-assembler.label.base-picker";
    private static final String TARGET_PICKER_LABEL_KEY = "exchange-batch-assembler.label.target-picker";
    private static final String START_DATE_PICKER_LABEL_KEY = "exchange-batch-assembler.label.start-date-picker";
    private static final String END_DATE_PICKER_LABEL_KEY = "exchange-batch-assembler.label.end-date-picker";
    private static final int CURRENCY_PICKER_COLSPAN = 2;

    private final SingleCurrencyPicker basePicker;
    private final MultiCurrencyPicker targetPicker;
    private final DatePicker startDatePicker;
    private final DatePicker endDatePicker;

    ////////////////
    // Public API //
    ////////////////

    public ExchangeBatchAssembler(@Nonnull CurrencyService currencyDataSource) {
        basePicker = new SingleCurrencyPicker(currencyDataSource);
        targetPicker = new MultiCurrencyPicker(currencyDataSource);
        startDatePicker = new DatePicker();
        endDatePicker = new DatePicker();

        // FIXME (Implementation): ValueChangeListener(s) for date chronology validation

        basePicker.setRequired(true);
        targetPicker.setRequired(true);
        startDatePicker.setRequired(true);
        endDatePicker.setRequired(true);

        FormLayout layout = new FormLayout(basePicker, targetPicker, startDatePicker, endDatePicker);
        layout.setColspan(basePicker, CURRENCY_PICKER_COLSPAN);
        layout.setColspan(targetPicker, CURRENCY_PICKER_COLSPAN);
        add(layout);
    }

    public void reloadAvailableCurrencies() {
        basePicker.reloadAvailableCurrencies();
        targetPicker.reloadAvailableCurrencies();
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        basePicker.localeChange(event);
        I18NUtilities.setLabel(basePicker, event, BASE_PICKER_LABEL_KEY);

        targetPicker.localeChange(event);
        I18NUtilities.setLabel(targetPicker, event, TARGET_PICKER_LABEL_KEY);

        startDatePicker.setLocale(event.getLocale());
        I18NUtilities.setLabel(startDatePicker, event, START_DATE_PICKER_LABEL_KEY);

        endDatePicker.setLocale(event.getLocale());
        I18NUtilities.setLabel(endDatePicker, event, END_DATE_PICKER_LABEL_KEY);
    }

    //////////////////////////
    // Implementation stuff //
    //////////////////////////

    @Override
    protected @Nullable ExchangeBatch generateModelValue() {
        // TODO (Performance): Eager isEmpty checks on each value and fail-fast if true
        Optional<Currency> base = basePicker.getOptionalValue();
        Set<Currency> targets = targetPicker.getSelectedItems();
        Optional<LocalDate> start = startDatePicker.getOptionalValue();
        Optional<LocalDate> end = endDatePicker.getOptionalValue();

        return base.isPresent() && !targets.isEmpty() && start.isPresent() && end.isPresent()
                ? new ExchangeBatch(base.get(), targets, new LocalDateInterval(start.get(), end.get()))
                : null;
    }

    @Override
    protected void setPresentationValue(@Nullable ExchangeBatch newPresentationValue) {
        if (newPresentationValue == null) {
            basePicker.clear();
            targetPicker.clear();
            startDatePicker.clear();
            endDatePicker.clear();
        } else {
            basePicker.setValue(newPresentationValue.base());
            targetPicker.setValue(newPresentationValue.targets());

            LocalDateInterval dateInterval = newPresentationValue.dateInterval();
            startDatePicker.setValue(dateInterval.start());
            endDatePicker.setValue(dateInterval.end());
        }
    }
}
