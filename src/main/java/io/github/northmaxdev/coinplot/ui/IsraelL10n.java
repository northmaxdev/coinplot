// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.ui;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.combobox.ComboBox.ComboBoxI18n;
import com.vaadin.flow.component.combobox.MultiSelectComboBoxI18n;
import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;
import io.github.northmaxdev.coinplot.util.Locales;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Currency;
import java.util.List;
import java.util.stream.Stream;

import static java.time.DayOfWeek.*;

public final class IsraelL10n {

    public static final ItemLabelGenerator<Currency> CURRENCY_LABEL_GENERATOR = currency -> currency.getDisplayName(Locales.ISRAEL);

    public static final ComboBoxI18n COMBO_BOX_L10N = new ComboBoxI18n()
            .setRequiredErrorMessage("נא לבחור פריט מהרשימה");

    public static final MultiSelectComboBoxI18n MULTI_SELECT_COMBO_BOX_L10N = new MultiSelectComboBoxI18n()
            .setRequiredErrorMessage("נא לבחור לפחות פריט אחד מהרשימה");

    public static final DatePickerI18n DATE_PICKER_L10N = new DatePickerI18n()
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

    private IsraelL10n() {}

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
}
