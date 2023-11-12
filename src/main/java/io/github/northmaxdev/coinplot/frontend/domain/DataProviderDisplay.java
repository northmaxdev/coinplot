// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.DataProvider;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import jakarta.annotation.Nonnull;

public final class DataProviderDisplay extends TextField implements LocaleChangeObserver {

    private static final String LABEL_KEY = "data-provider-display.label";

    public DataProviderDisplay() {
        setReadOnly(true);
        setPrefixComponent(VaadinIcon.DATABASE.create());
    }

    public DataProviderDisplay(@Nonnull DataProvider dataProvider) {
        this();
        display(dataProvider);
    }

    public void display(@Nonnull DataProvider dataProvider) {
        setValue(dataProvider.getDisplayName());
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        I18NUtilities.setLabel(this, event, LABEL_KEY);
    }
}
