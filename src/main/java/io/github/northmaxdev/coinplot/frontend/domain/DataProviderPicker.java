// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain;

import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import io.github.northmaxdev.coinplot.backend.core.DataProvider;
import io.github.northmaxdev.coinplot.frontend.common.ListDataProviders;
import jakarta.annotation.Nonnull;

import java.util.Comparator;

public final class DataProviderPicker extends RadioButtonGroup<DataProvider> {

    private static final Comparator<DataProvider> SORT_COMPARATOR = Comparator.comparing(DataProvider::getDisplayName);

    public DataProviderPicker(@Nonnull DataProvider... dataProviders) {
        setItems(ListDataProviders.create(SORT_COMPARATOR, dataProviders));
        setItemLabelGenerator(DataProvider::getDisplayName);
    }
}
