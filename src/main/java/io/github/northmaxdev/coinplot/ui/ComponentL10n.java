// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.ComboBox.ComboBoxI18n;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBoxI18n;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;
import io.github.northmaxdev.coinplot.util.Locales;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Currency;
import java.util.List;
import java.util.stream.Stream;

import static java.time.DayOfWeek.*;

public final class ComponentL10n {

    ////////////////
    // Public API //
    ////////////////

    public static void localize(Component component) {
        switch (component) {
            case ComboBox<?> comboBox -> comboBox.setI18n(COMBO_BOX_L10N);
            case MultiSelectComboBox<?> multiSelectComboBox -> {
                multiSelectComboBox.setI18n(MULTI_SELECT_COMBO_BOX_L10N);
                multiSelectComboBox.setHelperText("ניתן לבחור יותר מאפשרות אחת");
            }
            case DatePicker datePicker -> {
                datePicker.setI18n(DATE_PICKER_L10N);
                datePicker.setLocale(Locales.ISRAEL);
                // TODO: Set some tooltip text to override the default English one
            }
            default -> {}
        }
    }

    // FIXME: Components will have to manually set this in addition to calling localize().
    //  Reasons:
    //  1. Java type erasure (no switch pattern)
    //  2. No common interface between classes that use ItemLabelGenerator, such as Select<T> or ComboBoxBase<T>
    public static final ItemLabelGenerator<Currency> CURRENCY_LABEL_GENERATOR = currency -> currency.getDisplayName(Locales.ISRAEL);

    ////////////////////////////
    // Implementation details //
    ////////////////////////////

    private static final ComboBoxI18n COMBO_BOX_L10N = new ComboBoxI18n()
            .setRequiredErrorMessage("חובה לבחור פריט מהרשימה");

    // All other MultiSelectComboBoxI18n properties are related to screen-readers
    private static final MultiSelectComboBoxI18n MULTI_SELECT_COMBO_BOX_L10N = new MultiSelectComboBoxI18n()
            .setRequiredErrorMessage("חובה לבחור לפחות פריט אחד מהרשימה");

    private static final DatePickerI18n DATE_PICKER_L10N = new DatePickerI18n()
            .setMonthNames(getMonthNamesStartingJanuary())
            .setWeekdays(getWeekdayNamesStartingSunday(TextStyle.FULL))
            .setWeekdaysShort(getWeekdayNamesStartingSunday(TextStyle.NARROW))
            .setDateFormat(null)
            .setFirstDayOfWeek(0)
            .setToday("היום")
            .setCancel("ביטול")
            .setBadInputErrorMessage("תאריך לא תקין")
            .setRequiredErrorMessage("נא לבחור או להזין תאריך")
            .setMinErrorMessage("נא לבחור או להזין תאריך מאוחר יותר")
            .setMaxErrorMessage("נא לבחור או להזין תאריך מוקדם יותר");

    private static List<String> getMonthNamesStartingJanuary() {
        return Stream.of(Month.values())
                .map(month -> month.getDisplayName(TextStyle.FULL, Locales.ISRAEL))
                .toList();
    }

    private static List<String> getWeekdayNamesStartingSunday(TextStyle style) {
        return Stream.of(SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY)
                .map(weekday -> weekday.getDisplayName(style, Locales.ISRAEL))
                .toList();
    }

    private ComponentL10n() {}
}
