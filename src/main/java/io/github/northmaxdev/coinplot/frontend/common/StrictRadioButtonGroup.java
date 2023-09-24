// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import jakarta.annotation.Nonnull;

import java.util.Collection;
import java.util.List;

// A RadioButtonGroup with extra instantiation-time requirements to ensure more optimal UI/UX.
public class StrictRadioButtonGroup<T> extends RadioButtonGroup<T> { // Open (non-final)

    private static final int MIN_ITEMS = 2;

    @SafeVarargs
    public StrictRadioButtonGroup(@Nonnull T... items) {
        if (items.length < MIN_ITEMS) { // Implicit null-check on the array itself
            throw new IllegalArgumentException("Must have at least " + MIN_ITEMS + " items");
        }

        // Explicitly use an immutable Collection (i.e., do not rely on Vaadin implementation details).
        // This also implicitly null-checks the array's elements.
        Collection<T> backingCollection = List.of(items);
        setItems(backingCollection);
    }
}
