// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.SerializableComparator;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import jakarta.annotation.Nonnull;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public final class LocalePicker extends Select<Locale> implements LocaleChangeObserver {

    private static final String LABEL_KEY = "locale-picker.label";
    private static final SerializableComparator<Locale> SORT_COMPARATOR = Comparator.comparing(Locale::getLanguage)::compare;
    private static final ItemLabelGenerator<Locale> ITEM_LABEL_GENERATOR = locale -> locale.getDisplayName(locale);

    public LocalePicker(@Nonnull Collection<Locale> locales) {
        // Protective copy + deep null-checks
        Collection<Locale> copiedItems = List.copyOf(locales);

        ListDataProvider<Locale> dataProvider = new ListDataProvider<>(copiedItems);
        dataProvider.setSortComparator(SORT_COMPARATOR);
        setItems(dataProvider);

        setItemLabelGenerator(ITEM_LABEL_GENERATOR);
        setPrefixComponent(VaadinIcon.GLOBE.create());
    }

    public static @Nonnull LocalePicker fromI18NProvider(@Nonnull I18NProvider p) {
        return new LocalePicker(p.getProvidedLocales());
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        I18NUtilities.setLabel(this, event, LABEL_KEY);
    }
}
