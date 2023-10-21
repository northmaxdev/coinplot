// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeBatch;
import io.github.northmaxdev.coinplot.frontend.common.LocalizedButton;
import io.github.northmaxdev.coinplot.frontend.domain.currency.MultiCurrencyPicker;
import io.github.northmaxdev.coinplot.frontend.domain.currency.SingleCurrencyPicker;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import io.github.northmaxdev.coinplot.lang.chrono.LocalDateInterval;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public final class ExchangeBatchSubmissionForm extends FormLayout implements LocaleChangeObserver {

    private static final String BASE_CURRENCY_PICKER_LABEL_KEY = "exchange-batch-submission-form.base-currency-picker.label";
    private static final String TARGET_CURRENCY_PICKER_LABEL_KEY = "exchange-batch-submission-form.target-currency-picker.label";
    private static final String START_DATE_PICKER_LABEL_KEY = "exchange-batch-submission-form.start-date-picker.label";
    private static final String END_DATE_PICKER_LABEL_KEY = "exchange-batch-submission-form.end-date-picker.label";
    private static final String SUBMIT_BUTTON_TEXT_KEY = "exchange-batch-submission-form.submit-button.text";
    private static final String CLEAR_BUTTON_TEXT_KEY = "exchange-batch-submission-form.clear-button.text";
    private static final String CURRENCY_RELOAD_BUTTON_TEXT_KEY = "exchange-batch-submission-form.currency-reload-button.text";
    private static final int CURRENCY_PICKER_COLSPAN = 2;

    private final SingleCurrencyPicker baseCurrencyPicker;
    private final MultiCurrencyPicker targetCurrencyPicker;
    private final DatePicker startDatePicker;
    private final DatePicker endDatePicker;
    private final LocalizedButton submitButton;
    private final LocalizedButton clearButton;
    private final LocalizedButton currencyReloadButton;
    // onPresentValueSubmission is used when the created ExchangeBatch is non-null.
    // onAbsentValueSubmission is used when the created ExchangeBatch is null.
    // The only reason for the created ExchangeBatch to be null is if the form was
    // filled in incorrectly, if at all. This means that if onAbsentValueSubmission
    // includes some kind of textual notification to the user, it's OK to tailor
    // that message specifically for this scenario.
    private final Consumer<ExchangeBatch> onPresentValueSubmission;
    private final Runnable onAbsentValueSubmission;

    public ExchangeBatchSubmissionForm(
            @Nonnull CurrencyService currencyDataSource,
            @Nonnull Consumer<ExchangeBatch> onPresentValueSubmission,
            @Nonnull Runnable onAbsentValueSubmission) {

        //////////////////////////
        // Field initialization //
        //////////////////////////

        baseCurrencyPicker = new SingleCurrencyPicker(currencyDataSource);
        targetCurrencyPicker = new MultiCurrencyPicker(currencyDataSource);
        startDatePicker = new DatePicker();
        endDatePicker = new DatePicker();
        submitButton = new LocalizedButton(SUBMIT_BUTTON_TEXT_KEY, VaadinIcon.CHECK);
        clearButton = new LocalizedButton(CLEAR_BUTTON_TEXT_KEY, VaadinIcon.CLOSE);
        currencyReloadButton = new LocalizedButton(CURRENCY_RELOAD_BUTTON_TEXT_KEY, VaadinIcon.DOWNLOAD_ALT);
        this.onPresentValueSubmission = Objects.requireNonNull(onPresentValueSubmission);
        this.onAbsentValueSubmission = Objects.requireNonNull(onAbsentValueSubmission);

        /////////////////////
        // Event listeners //
        /////////////////////

        // TODO:
        //  Add ValueChangeListener to baseCurrencyPicker that
        //  disables that same currency in targetCurrencyPicker

        // TODO: Add some variety to button listeners (single clicks, double clicks, etc.)
        submitButton.addSingleClickListener(event -> submit());
        clearButton.addSingleClickListener(event -> clear());
        currencyReloadButton.addSingleClickListener(event -> reloadAvailableCurrencies());

        ////////////////////
        // Visual markers //
        ////////////////////

        // TODO:
        //  Visual markers (helper text or error message)
        //  regarding the dates' chronological validity

        baseCurrencyPicker.setRequired(true);
        targetCurrencyPicker.setRequired(true);
        startDatePicker.setRequired(true);
        endDatePicker.setRequired(true);
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        clearButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        // Use the default variant for the reload button

        //////////////////////////
        // Layout configuration //
        //////////////////////////

        // Official Vaadin guidelines regarding buttons in forms:
        // https://vaadin.com/docs/latest/components/form-layout#button-placement
        HorizontalLayout buttonBar = new HorizontalLayout(submitButton, clearButton, currencyReloadButton);
        buttonBar.setPadding(false); // This is so it's horizontally in line with the rest of the form's components
        buttonBar.setJustifyContentMode(FlexComponent.JustifyContentMode.START);

        add(baseCurrencyPicker, targetCurrencyPicker, startDatePicker, endDatePicker, buttonBar);
        setColspan(baseCurrencyPicker, CURRENCY_PICKER_COLSPAN);
        setColspan(targetCurrencyPicker, CURRENCY_PICKER_COLSPAN);
    }

    public void submit() {
        @Nullable ExchangeBatch exchangeBatch = createExchangeBatch();
        if (exchangeBatch == null) {
            onAbsentValueSubmission.run();
        } else {
            onPresentValueSubmission.accept(exchangeBatch);
        }
    }

    public void clear() {
        baseCurrencyPicker.clear();
        targetCurrencyPicker.clear();
        startDatePicker.clear();
        endDatePicker.clear();
    }

    public void reloadAvailableCurrencies() {
        baseCurrencyPicker.reloadAvailableCurrencies();
        targetCurrencyPicker.reloadAvailableCurrencies();
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        baseCurrencyPicker.localeChange(event);
        I18NUtilities.setLabel(baseCurrencyPicker, event, BASE_CURRENCY_PICKER_LABEL_KEY);

        targetCurrencyPicker.localeChange(event);
        I18NUtilities.setLabel(targetCurrencyPicker, event, TARGET_CURRENCY_PICKER_LABEL_KEY);

        startDatePicker.setLocale(event.getLocale());
        I18NUtilities.setLabel(startDatePicker, event, START_DATE_PICKER_LABEL_KEY);

        endDatePicker.setLocale(event.getLocale());
        I18NUtilities.setLabel(endDatePicker, event, END_DATE_PICKER_LABEL_KEY);

        submitButton.localeChange(event);
        clearButton.localeChange(event);
        currencyReloadButton.localeChange(event);
    }

    private @Nullable ExchangeBatch createExchangeBatch() {
        @Nullable Currency baseCurrency = baseCurrencyPicker.getValue();
        @Nonnull Set<Currency> targetCurrencies = targetCurrencyPicker.getSelectedItems();
        @Nullable LocalDate startDate = startDatePicker.getValue();
        @Nullable LocalDate endDate = endDatePicker.getValue();

        if (baseCurrency == null || targetCurrencies.isEmpty() || LocalDateInterval.areDatesInvalid(startDate, endDate)) {
            return null;
        }

        LocalDateInterval dateInterval = new LocalDateInterval(startDate, endDate);
        return new ExchangeBatch(baseCurrency, targetCurrencies, dateInterval);
    }
}