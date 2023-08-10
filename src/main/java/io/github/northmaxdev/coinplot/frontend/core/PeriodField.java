// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.core;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.lang.Ints;

import java.time.Period;
import java.time.temporal.ChronoUnit;

public final class PeriodField extends CustomField<Period> implements LocaleChangeObserver {

    private final ChronoUnitSelect unitSelect;
    private final IntegerField unitAmountField;

    public PeriodField() {
        this.unitSelect = ChronoUnitSelect.with(ChronoUnit.DAYS, ChronoUnit.MONTHS, ChronoUnit.YEARS);
        this.unitAmountField = new IntegerField();

        add(unitSelect, unitAmountField);
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        unitSelect.localeChange(event);
    }

    @Override
    protected Period generateModelValue() {
        int unitAmount = Ints.zeroIfNull(unitAmountField.getValue());
        return switch (unitSelect.getValue()) {
            case null -> null;
            case DAYS -> Period.ofDays(unitAmount);
            case MONTHS -> Period.ofMonths(unitAmount);
            case YEARS -> Period.ofYears(unitAmount);
            default -> throw new IllegalStateException("Unsupported ChronoUnit");
        };
    }

    @Override
    protected void setPresentationValue(Period period) {
        // TODO:
        //  Convert a given java.time.Period into a single-unit representation (truncating if
        //  amount exceeds Integer.MAX_VALUE is OK so long as the user is notified about it).
        //  Example: Period.of(2, 5, 12) --> Period.of(0, 0, 892)
    }
}
