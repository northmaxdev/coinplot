// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.core;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.frontend.core.ThemableLayouts.Spacing;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import io.github.northmaxdev.coinplot.lang.Ints;
import io.github.northmaxdev.coinplot.lang.chrono.Periods;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.OptionalInt;

// NOTE:
// It's still possible to get nulls if calling setValue(null) or doing something similar.
// This is due to Vaadin's CustomField and AbstractField APIs, which I'm not going to delve into that deeply.
// Prefer using clear() or setValue(Period.ZERO) unless you like getting NPEs.
public final class PeriodField extends CustomField<Period> implements LocaleChangeObserver {

    private static final String HELPER_TEXT_KEY = "period-field.helper-text";

    private final ChronoUnitPicker unitPicker;
    private final IntegerField unitAmountField;

    private int days = 0;
    private int months = 0;
    private int years = 0;

    public PeriodField() {
        this(Period.ZERO);
    }

    public PeriodField(@Nullable Period initialValue) {
        this.unitPicker = new ChronoUnitPicker(ChronoUnit.DAYS, ChronoUnit.MONTHS, ChronoUnit.YEARS);
        this.unitAmountField = new IntegerField();

        unitPicker.addValueChangeListener(event -> {
            @Nullable ChronoUnit newUnitSelection = event.getValue();
            updateAmountFieldForUnit(newUnitSelection);
        });

        unitAmountField.addValueChangeListener(event -> {
            if (event.isFromClient()) {
                Optional<ChronoUnit> selectedUnit = unitPicker.getOptionalValue();
                int newAmount = Ints.zeroIfNull(event.getValue());
                selectedUnit.ifPresent(unit -> setSavedUnitAmount(unit, newAmount));
            }
        });

        unitAmountField.setStepButtonsVisible(true);
        HorizontalLayout layout = ThemableLayouts.createHorizontal(Spacing.EXTRA_SMALL, unitPicker, unitAmountField);
        add(layout);

        setValue(Periods.zeroIfNull(initialValue));
    }

    //////////////////////
    // Model/view stuff //
    //////////////////////

    @Override
    public @Nonnull Period getEmptyValue() {
        return Period.ZERO;
    }

    @Override
    protected @Nonnull Period generateModelValue() {
        return Period.of(years, months, days);
    }

    @Override
    protected void setPresentationValue(@Nullable Period newPresentationValue) {
        @Nonnull Period period = Periods.zeroIfNull(newPresentationValue);
        days = period.getDays();
        months = period.getMonths();
        years = period.getYears();

        @Nullable ChronoUnit currentlySelectedUnit = unitPicker.getValue();
        updateAmountFieldForUnit(currentlySelectedUnit);
    }

    private void updateAmountFieldForUnit(@Nullable ChronoUnit unit) {
        OptionalInt amount = getSavedUnitAmount(unit);
        amount.ifPresentOrElse(unitAmountField::setValue, unitAmountField::clear);
    }

    //////////
    // I18N //
    //////////

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        unitPicker.localeChange(event);
        I18NUtilities.setHelperText(this, event, HELPER_TEXT_KEY);
    }

    //////////////////////////////////////////////////
    // Read/write on internally stored Y/M/D values //
    //////////////////////////////////////////////////

    private OptionalInt getSavedUnitAmount(@Nullable ChronoUnit unit) {
        return switch (unit) {
            case DAYS -> OptionalInt.of(days);
            case MONTHS -> OptionalInt.of(months);
            case YEARS -> OptionalInt.of(years);
            case null, default -> OptionalInt.empty();
        };
    }

    private void setSavedUnitAmount(@Nonnull ChronoUnit unit, int amount) {
        switch (unit) {
            case DAYS -> days = amount;
            case MONTHS -> months = amount;
            case YEARS -> years = amount;
            default -> throw new IllegalStateException("Unexpected unit: " + unit);
        }
    }
}
