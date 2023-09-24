// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.data.provider.ListDataProvider;
import jakarta.annotation.Nonnull;

import java.util.Comparator;

// A Vaadin RadioButtonGroup with extra instantiation-time requirements to ensure more optimal UI/UX.
// Type T must have well-defined equals/hashCode implementations.
public class StrictRadioButtonGroup<T> extends RadioButtonGroup<T> { // Open (non-final)

    private static final int MIN_OPTIONS = 2;

    @SafeVarargs
    public StrictRadioButtonGroup(@Nonnull ItemLabelGenerator<T> labelGenerator,
                                  @Nonnull Comparator<T> sortComparator,
                                  @Nonnull T... options) {
        if (options.length < MIN_OPTIONS) { // Implicit null-check on the array itself
            throw new IllegalArgumentException("Must have at least " + MIN_OPTIONS + " options");
        }

        ListDataProvider<T> dataProvider = ListDataProviders.create(sortComparator, options);
        setItems(dataProvider);
        setItemLabelGenerator(labelGenerator); // Implicit null-check
    }
}
