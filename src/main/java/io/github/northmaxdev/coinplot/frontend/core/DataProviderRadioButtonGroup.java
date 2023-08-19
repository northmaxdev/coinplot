// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.core;

import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.DataProvider;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import jakarta.annotation.Nonnull;

import java.util.Objects;

public final class DataProviderRadioButtonGroup extends RadioButtonGroup<DataProvider> implements LocaleChangeObserver {

    private static final String LABEL_KEY = "data-provider-radio-button-group.label";

    public DataProviderRadioButtonGroup(@Nonnull DataProvider... dataProviders) {
        Objects.requireNonNull(dataProviders, "dataProviders (varargs) is null");

        setItems(dataProviders);
        setItemLabelGenerator(DataProvider::getDisplayName);
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        I18NUtilities.setLabel(this, event, LABEL_KEY);
    }
}
