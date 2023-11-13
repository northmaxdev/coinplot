// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.SerializableComparator;
import jakarta.annotation.Nonnull;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public final class LocalePicker extends Select<Locale> {

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
}
