// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.core;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.lang.Ints;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.OptionalInt;

public final class PeriodField extends CustomField<Period> implements LocaleChangeObserver {

    private final ChronoUnitSelect unitSelect;
    private final IntegerField unitAmountField;

    private int days = 0;
    private int months = 0;
    private int years = 0;

    public PeriodField() {
        this.unitSelect = ChronoUnitSelect.with(ChronoUnit.DAYS, ChronoUnit.MONTHS, ChronoUnit.YEARS);
        this.unitAmountField = new IntegerField();

        unitSelect.addValueChangeListener(event -> {
            @Nullable ChronoUnit newUnitSelection = event.getValue();
            updateAmountFieldForUnit(newUnitSelection);
        });

        unitAmountField.addValueChangeListener(event -> {
            Optional<ChronoUnit> selectedUnit = unitSelect.getOptionalValue();
            int newAmount = Ints.zeroIfNull(event.getValue());
            selectedUnit.ifPresent(unit -> setSavedUnitAmount(unit, newAmount));
        });

        //       +---------------------------+
        //       | Event: new unit selection |
        //       +-+------------------------++
        //         |                        |
        //     +---v---+           +--------v-----------+
        //     | Y/M/D |           | null or unexpected |
        //     +---+---+           +--------+-----------+
        //         |                        |
        //     +---v------+            +----v--+
        //     | setValue |            | clear |
        //     +---+------+            +----+--+
        //         |                        |
        //         |                        |
        //         |                        |
        //         |                        |
        //         |                        |
        //         |  +-------------------+ |
        //         +--> Event: new amount <-+
        //            +---------+---------+
        //                      |
        //        +-------------v---------------+
        //        | Get currently selected unit |
        //        +---+----------------------+--+
        //            |                      |
        //       +----v-----+            +---v--+
        //       | non-null |            | null |
        //       +----+-----+            +---+--+
        //            |                      |
        //            |                      |
        // +----------v----------+     +-----v--+
        // | update saved values |     | ignore |
        // +---------------------+     +--------+

        add(unitSelect, unitAmountField);

        unitAmountField.setStepButtonsVisible(true);
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        unitSelect.localeChange(event);
    }

    @Override
    public Period getEmptyValue() {
        return Period.ZERO;
    }

    ///////////////////////
    // CustomField stuff //
    ///////////////////////

    @Override
    protected Period generateModelValue() {
        return Period.of(years, months, days);
    }

    @Override
    protected void setPresentationValue(Period period) {
        if (period == null) {
            days = 0;
            months = 0;
            years = 0;
        } else {
            days = period.getDays();
            months = period.getMonths();
            years = period.getYears();
        }

        @Nullable ChronoUnit currentlySelectedUnit = unitSelect.getValue();
        updateAmountFieldForUnit(currentlySelectedUnit);
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

    /////////////////////////////////
    // Internal UI component stuff //
    /////////////////////////////////

    private void updateAmountFieldForUnit(@Nullable ChronoUnit unit) {
        OptionalInt amount = getSavedUnitAmount(unit);
        amount.ifPresentOrElse(unitAmountField::setValue, unitAmountField::clear);
    }
}
