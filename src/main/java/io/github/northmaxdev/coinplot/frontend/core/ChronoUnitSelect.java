// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.core;

import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableSet;

public final class ChronoUnitSelect extends Select<ChronoUnit> implements LocaleChangeObserver {

    public static ChronoUnitSelect forLocalDate() {
        // Use a dummy value since LocalDate::isSupported is an instance method
        return filtered(LocalDate.EPOCH::isSupported);
    }

    public static ChronoUnitSelect filtered(Predicate<ChronoUnit> filter) {
        Collection<ChronoUnit> units = Stream.of(ChronoUnit.values())
                .filter(filter)
                .collect(toUnmodifiableSet());

        return new ChronoUnitSelect(units);
    }

    private ChronoUnitSelect(Collection<ChronoUnit> units) {
        setItemLabelGenerator(unit -> getTranslatedUnitLabel(unit, getLocale()));
        setItems(units);
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        setItemLabelGenerator(unit -> getTranslatedUnitLabel(unit, event.getLocale()));
    }

    private String getTranslatedUnitLabel(ChronoUnit unit, Locale locale) {
        String propertyKey = switch (unit) {
            case NANOS -> "chrono-unit-select.unit-label.nanos";
            case MICROS -> "chrono-unit-select.unit-label.micros";
            case MILLIS -> "chrono-unit-select.unit-label.millis";
            case SECONDS -> "chrono-unit-select.unit-label.seconds";
            case MINUTES -> "chrono-unit-select.unit-label.minutes";
            case HOURS -> "chrono-unit-select.unit-label.hours";
            case HALF_DAYS -> "chrono-unit-select.unit-label.half-days";
            case DAYS -> "chrono-unit-select.unit-label.days";
            case WEEKS -> "chrono-unit-select.unit-label.weeks";
            case MONTHS -> "chrono-unit-select.unit-label.months";
            case YEARS -> "chrono-unit-select.unit-label.years";
            case DECADES -> "chrono-unit-select.unit-label.decades";
            case CENTURIES -> "chrono-unit-select.unit-label.centuries";
            case MILLENNIA -> "chrono-unit-select.unit-label.millennia";
            case ERAS -> "chrono-unit-select.unit-label.eras";
            case FOREVER -> "chrono-unit-select.unit-label.forever";
        };

        return getTranslation(locale, propertyKey);
    }
}
