// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.core;

import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Set;

public final class ChronoUnitPicker extends Select<ChronoUnit> implements LocaleChangeObserver {

    public ChronoUnitPicker(@Nonnull ChronoUnit... units) {
        this(Set.of(units)); // Implicit null-check(s)
    }

    public ChronoUnitPicker(@Nonnull Set<ChronoUnit> units) {
        Set<ChronoUnit> copiedItems = Set.copyOf(units); // Implicit null-check(s)

        ListDataProvider<ChronoUnit> dataProvider = new ListDataProvider<>(copiedItems);
        dataProvider.setSortComparator(Enum::compareTo);
        setItems(dataProvider);

        setEmptySelectionAllowed(false);
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        setItemLabelGenerator(unit -> getTranslatedUnitLabel(unit, event.getLocale()));
    }

    private @Nonnull String getTranslatedUnitLabel(@Nullable ChronoUnit unit, @Nonnull Locale locale) {
        // No need to null-check locale
        if (unit == null) {
            return "";
        }

        String i18nKey = switch (unit) {
            case NANOS -> "chrono-unit-picker.item-label.nanos";
            case MICROS -> "chrono-unit-picker.item-label.micros";
            case MILLIS -> "chrono-unit-picker.item-label.millis";
            case SECONDS -> "chrono-unit-picker.item-label.seconds";
            case MINUTES -> "chrono-unit-picker.item-label.minutes";
            case HOURS -> "chrono-unit-picker.item-label.hours";
            case HALF_DAYS -> "chrono-unit-picker.item-label.half-days";
            case DAYS -> "chrono-unit-picker.item-label.days";
            case WEEKS -> "chrono-unit-picker.item-label.weeks";
            case MONTHS -> "chrono-unit-picker.item-label.months";
            case YEARS -> "chrono-unit-picker.item-label.years";
            case DECADES -> "chrono-unit-picker.item-label.decades";
            case CENTURIES -> "chrono-unit-picker.item-label.centuries";
            case MILLENNIA -> "chrono-unit-picker.item-label.millennia";
            case ERAS -> "chrono-unit-picker.item-label.eras";
            case FOREVER -> "chrono-unit-picker.item-label.forever";
        };

        return getTranslation(locale, i18nKey);
    }
}
