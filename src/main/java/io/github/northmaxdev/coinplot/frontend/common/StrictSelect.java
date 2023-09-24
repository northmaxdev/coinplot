// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.provider.ListDataProvider;
import jakarta.annotation.Nonnull;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

// A Vaadin Select with extra instantiation-time requirements to ensure more optimal UI/UX.
// Type T must have well-defined equals/hashCode implementations (see ListDataProviders, which is used by this class).
public class StrictSelect<T> extends Select<T> { // Open (non-final)

    private static final int MIN_OPTIONS = 3;

    @SafeVarargs
    public StrictSelect(@Nonnull ItemLabelGenerator<T> labelGenerator,
                        @Nonnull Comparator<T> sortComparator,
                        @Nonnull T... options) {
        StrictComponents.checkOptionCount(MIN_OPTIONS, options);
        setup(labelGenerator, ListDataProviders.create(sortComparator, options));
    }

    public StrictSelect(@Nonnull ItemLabelGenerator<T> labelGenerator,
                        @Nonnull Comparator<T> sortComparator,
                        @Nonnull Collection<T> options) {
        StrictComponents.checkOptionCount(MIN_OPTIONS, options);
        setup(labelGenerator, ListDataProviders.create(sortComparator, options));
    }

    private void setup(@Nonnull ItemLabelGenerator<T> labelGenerator, @Nonnull ListDataProvider<T> dataProvider) {
        // Select::setItemLabelGenerator is completely OK with nulls
        // (this may differ from other components with a setItemLabelGenerator method, e.g., RadioButtonGroup).
        // Therefore, explicitly null-check.
        Objects.requireNonNull(labelGenerator);
        setItemLabelGenerator(labelGenerator);

        setItems(dataProvider); // No need to null-check
    }
}
