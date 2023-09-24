// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain;

import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.DataProvider;
import io.github.northmaxdev.coinplot.frontend.common.StrictRadioButtonGroup;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import jakarta.annotation.Nonnull;

// TODO: Well-defined equals/hashCode for DataProvider implementations (see StrictRadioButtonGroup comments)
public final class DataProviderPicker extends StrictRadioButtonGroup<DataProvider> implements LocaleChangeObserver {

    private static final String HELPER_TEXT_KEY = "data-provider-picker.helper-text";

    public DataProviderPicker(@Nonnull DataProvider... items) {
        super(items);
        setItemLabelGenerator(DataProvider::getDisplayName);
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        I18NUtilities.setHelperText(this, event, HELPER_TEXT_KEY);
    }
}
