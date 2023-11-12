// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.DataProvider;
import io.github.northmaxdev.coinplot.frontend.common.AbstractTextualDisplay;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public final class DataProviderTextualDisplay
        extends AbstractTextualDisplay<DataProvider>
        implements LocaleChangeObserver {

    private static final String LABEL_KEY = "data-provider-textual-display.label";

    public DataProviderTextualDisplay() {
        super();
    }

    public DataProviderTextualDisplay(@Nullable DataProvider item) {
        super(item);
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        I18NUtilities.setLabel(this, event, LABEL_KEY);
    }

    @Override
    protected @Nonnull Icon createIcon() {
        return VaadinIcon.DATABASE.create();
    }

    @Override
    protected @Nonnull String convertItemToText(@Nonnull DataProvider item) {
        return item.getDisplayName();
    }
}
