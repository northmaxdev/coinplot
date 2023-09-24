// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Locale;

public final class ChronoUnitPicker extends StrictSelect<ChronoUnit> implements LocaleChangeObserver {

    public ChronoUnitPicker(@Nonnull ChronoUnit... units) {
        // ChronoUnit::toString is a "throwaway" ItemLabelGenerator that we pass only
        // because we cannot reference 'this' before supertype constructor is called.
        super(ChronoUnit::toString, Comparator.naturalOrder(), units);
        setItemLabelGenerator(this::getTranslatedUnitLabel);
        setEmptySelectionAllowed(false);
    }

    ////////////////
    // L10N stuff //
    ////////////////

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        setItemLabelGenerator(unit -> {
            Locale locale = event.getLocale();
            return getTranslatedUnitLabel(unit, locale);
        });
    }

    private @Nonnull String getTranslatedUnitLabel(@Nullable ChronoUnit unit) {
        return getTranslatedUnitLabel(unit, getLocale());
    }

    private @Nonnull String getTranslatedUnitLabel(@Nullable ChronoUnit unit, @Nonnull Locale locale) {
        // No need to null-check locale, this method is used
        // only in contexts where it is guaranteed to be not null.

        if (unit == null) {
            return "";
        }

        @Nonnull String propertyKey = switch (unit) {
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

        return getTranslation(locale, propertyKey);
    }
}
