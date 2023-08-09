// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.core;

import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import jakarta.annotation.Nullable;

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Collection;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableSet;

public final class ChronoUnitSelect extends Select<ChronoUnit> implements LocaleChangeObserver {

    public static ChronoUnitSelect with(ChronoUnit... units) {
        Set<ChronoUnit> unitSet = Set.of(units);
        return new ChronoUnitSelect(unitSet);
    }

    public static ChronoUnitSelect withMatching(Predicate<ChronoUnit> predicate) {
        Collection<ChronoUnit> units = Stream.of(ChronoUnit.values())
                .filter(predicate)
                .collect(toUnmodifiableSet());
        return new ChronoUnitSelect(units);
    }

    private ChronoUnitSelect(Collection<ChronoUnit> units) {
        setItemLabelGenerator(unit -> getTranslatedUnitLabel(unit, getLocale()));

        ListDataProvider<ChronoUnit> dataProvider = new ListDataProvider<>(units);
        dataProvider.setSortComparator(Enum::compareTo);
        setItems(dataProvider);

        setEmptySelectionAllowed(false);
    }

    public Optional<Period> mapSelectionToPeriod(int amountOfUnit) {
        @Nullable Period p = switch (getValue()) {
            case null -> null;
            case DAYS -> Period.ofDays(amountOfUnit);
            case MONTHS -> Period.ofMonths(amountOfUnit);
            case YEARS -> Period.ofYears(amountOfUnit);
            default -> throw new UnsupportedTemporalTypeException("Unsupported unit");
        };

        return Optional.ofNullable(p);
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        setItemLabelGenerator(unit -> getTranslatedUnitLabel(unit, event.getLocale()));
    }

    private String getTranslatedUnitLabel(ChronoUnit unit, Locale locale) {
        String propertyKey = switch (unit) {
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

        return getTranslation(locale, propertyKey);
    }
}
