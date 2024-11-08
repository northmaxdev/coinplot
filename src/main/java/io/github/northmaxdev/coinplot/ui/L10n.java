// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.ui;

import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;
import io.github.northmaxdev.coinplot.util.Locales;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.stream.Stream;

import static java.time.DayOfWeek.*;

public final class L10n {

    public static final DatePickerI18n ISRAEL_DATEPICKER_L10N;

    static {
        List<String> monthNames = Stream.of(Month.values())
                .map(month -> month.getDisplayName(TextStyle.FULL, Locales.ISRAEL))
                .toList();

        List<String> weekdayNames = getWeekdayNamesStartingSunday(TextStyle.FULL);
        List<String> shortWeekdayNames = getWeekdayNamesStartingSunday(TextStyle.NARROW);

        ISRAEL_DATEPICKER_L10N = new DatePickerI18n()
                .setMonthNames(monthNames) // Must be ordered January to December
                .setWeekdays(weekdayNames) // Must be ordered Sunday to Saturday
                .setWeekdaysShort(shortWeekdayNames) // Must be ordered Sunday to Saturday
                .setDateFormat(null)
                .setFirstDayOfWeek(0)
                .setToday("היום")
                .setCancel("ביטול")
                .setBadInputErrorMessage("תאריך לא תקין")
                .setRequiredErrorMessage("נא לבחור או להזין תאריך")
                .setMinErrorMessage("נא לבחור או להזין תאריך מאוחר יותר")
                .setMaxErrorMessage("נא לבחור או להזין תאריך מוקדם יותר");
    }

    private L10n() {
    }

    private static List<String> getWeekdayNamesStartingSunday(TextStyle style) {
        return Stream.of(SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY)
                .map(weekday -> weekday.getDisplayName(style, Locales.ISRAEL))
                .toList();
    }
}
