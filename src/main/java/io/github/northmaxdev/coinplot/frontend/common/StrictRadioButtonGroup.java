// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.data.provider.ListDataProvider;
import jakarta.annotation.Nonnull;

import java.util.Comparator;

// A Vaadin RadioButtonGroup with extra instantiation-time requirements to ensure more optimal UI/UX.
public class StrictRadioButtonGroup<T> extends RadioButtonGroup<T> { // Open (non-final)

    private static final int MIN_OPTIONS = 2;

    @SafeVarargs
    public StrictRadioButtonGroup(@Nonnull ItemLabelGenerator<T> labelGenerator,
                                  @Nonnull Comparator<T> sortComparator,
                                  @Nonnull T... options) {
        StrictComponents.checkOptionCount(MIN_OPTIONS, options);

        // RadioButtonGroup::setItemLabelGenerator does NOT accept nulls
        // (this may differ from other components with a setItemLabelGenerator method, e.g., Select).
        setItemLabelGenerator(labelGenerator); // Implicit null-check

        ListDataProvider<T> dataProvider = ListDataProviders.create(sortComparator, options);
        setItems(dataProvider);
    }
}
