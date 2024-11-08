// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.ui.i18n;

import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;
import io.github.northmaxdev.coinplot.util.Locales;

import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import static java.time.DayOfWeek.*;
import static java.time.Month.*;

public final class DatePickerI18nUtils {

    // NOTE: Must also set the locale on the DatePicker to fully localize it (e.g., these I18n configs don't cover date formats)

    ///////////////////////////
    // Hebrew/Israel (he_IL) //
    ///////////////////////////

    public static final DatePickerI18n HEBREW_ISRAEL;

    static {
        List<String> monthNames = getJdkMonthTranslations(Locales.HEBREW_ISRAEL);
        List<String> weekdays = getJdkWeekdayTranslations(Locales.HEBREW_ISRAEL, TextStyle.FULL);
        List<String> weekdaysShort = getJdkWeekdayTranslations(Locales.HEBREW_ISRAEL, TextStyle.NARROW);

        HEBREW_ISRAEL = new DatePickerI18n()
                .setMonthNames(monthNames)
                .setWeekdays(weekdays)
                .setWeekdaysShort(weekdaysShort)
                .setDateFormat(null)
                .setFirstDayOfWeek(0)
                .setToday("היום")
                .setCancel("ביטול")
                .setBadInputErrorMessage("תאריך לא תקין")
                .setRequiredErrorMessage("יש להזין תאריך")
                .setMinErrorMessage("נא להזין תאריך מאוחר יותר")
                .setMaxErrorMessage("נא להזין תאריך מוקדם יותר");
    }

    ////////////////////////////
    // Russian/Russia (ru_RU) //
    ////////////////////////////

    public static final DatePickerI18n RUSSIAN_RUSSIA;

    static {
        List<String> monthNames = getJdkMonthTranslations(Locales.RUSSIAN_RUSSIA);
        List<String> weekdays = getJdkWeekdayTranslations(Locales.RUSSIAN_RUSSIA, TextStyle.FULL);
        List<String> weekdaysShort = getJdkWeekdayTranslations(Locales.RUSSIAN_RUSSIA, TextStyle.SHORT); // Narrow is too brief

        RUSSIAN_RUSSIA = new DatePickerI18n()
                .setMonthNames(monthNames)
                .setWeekdays(weekdays)
                .setWeekdaysShort(weekdaysShort)
                .setDateFormat(null)
                .setFirstDayOfWeek(1)
                .setToday("Сегодня")
                .setCancel("Отмена")
                .setBadInputErrorMessage("Некорректно введена дата")
                .setRequiredErrorMessage("Введите дату")
                .setMinErrorMessage("Введите более позднюю дату")
                .setMaxErrorMessage("Введите более раннюю дату");
    }

    ////////////////////////////
    // Implementation details //
    ////////////////////////////

    private DatePickerI18nUtils() {
        throw new UnsupportedOperationException();
    }

    // To be used with setMonthNames; it's crucial for the order to be January to December
    private static List<String> getJdkMonthTranslations(Locale locale) {
        return Stream.of(JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER)
                .map(month -> month.getDisplayName(TextStyle.FULL, locale))
                .toList();
    }

    // To be used with setWeekdays and setWeekdaysShort; it's crucial for the order to be Sunday to Saturday
    private static List<String> getJdkWeekdayTranslations(Locale locale, TextStyle style) {
        return Stream.of(SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY)
                .map(weekday -> weekday.getDisplayName(style, locale))
                .toList();
    }
}
