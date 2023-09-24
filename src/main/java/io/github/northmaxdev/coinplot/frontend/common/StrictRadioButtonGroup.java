// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import jakarta.annotation.Nonnull;

import java.util.Collection;
import java.util.Set;

// A RadioButtonGroup with extra instantiation-time requirements to ensure more optimal UI/UX.
// Type T must have well-defined equals/hashCode implementations.
public class StrictRadioButtonGroup<T> extends RadioButtonGroup<T> { // Open (non-final)

    private static final int MIN_OPTIONS = 2;

    @SafeVarargs
    public StrictRadioButtonGroup(@Nonnull T... options) {
        if (options.length < MIN_OPTIONS) { // Implicit null-check on the array itself
            throw new IllegalArgumentException("Must have at least " + MIN_OPTIONS + " options");
        }

        // Explicitly use an immutable Collection (i.e., do not rely on Vaadin implementation details).
        // Since RadioButtonGroup is intended for mutually exclusive options, we use a Set.
        // This also implicitly null-checks the array's elements.
        Collection<T> backingCollection = Set.of(options);
        setItems(backingCollection);
    }
}
