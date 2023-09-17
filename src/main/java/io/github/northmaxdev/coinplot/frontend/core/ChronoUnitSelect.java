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

public final class ChronoUnitSelect extends Select<ChronoUnit> implements LocaleChangeObserver {

    public ChronoUnitSelect(@Nonnull ChronoUnit... units) {
        this(Set.of(units)); // Implicit null-check(s)
    }

    public ChronoUnitSelect(@Nonnull Set<ChronoUnit> units) {
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
            case NANOS -> "chrono-unit-select.item-label.nanos";
            case MICROS -> "chrono-unit-select.item-label.micros";
            case MILLIS -> "chrono-unit-select.item-label.millis";
            case SECONDS -> "chrono-unit-select.item-label.seconds";
            case MINUTES -> "chrono-unit-select.item-label.minutes";
            case HOURS -> "chrono-unit-select.item-label.hours";
            case HALF_DAYS -> "chrono-unit-select.item-label.half-days";
            case DAYS -> "chrono-unit-select.item-label.days";
            case WEEKS -> "chrono-unit-select.item-label.weeks";
            case MONTHS -> "chrono-unit-select.item-label.months";
            case YEARS -> "chrono-unit-select.item-label.years";
            case DECADES -> "chrono-unit-select.item-label.decades";
            case CENTURIES -> "chrono-unit-select.item-label.centuries";
            case MILLENNIA -> "chrono-unit-select.item-label.millennia";
            case ERAS -> "chrono-unit-select.item-label.eras";
            case FOREVER -> "chrono-unit-select.item-label.forever";
        };

        return getTranslation(locale, i18nKey);
    }
}
