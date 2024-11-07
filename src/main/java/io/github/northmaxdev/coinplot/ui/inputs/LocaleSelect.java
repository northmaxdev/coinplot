// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.ui.inputs;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.select.Select;

import java.util.Collection;
import java.util.Locale;

public final class LocaleSelect extends Select<Locale> {

    private static final ItemLabelGenerator<Locale> ITEM_LABEL_GENERATOR = locale -> locale.getDisplayName(locale);

    public LocaleSelect(Collection<Locale> locales) {
        setItems(locales);
        setItemLabelGenerator(ITEM_LABEL_GENERATOR);
        setPrefixComponent(VaadinIcon.GLOBE.create());
    }
}
