// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import com.vaadin.flow.component.ItemLabelGenerator;
import io.github.northmaxdev.coinplot.lang.TextuallyDisplayable;
import jakarta.annotation.Nonnull;

import java.util.Locale;

public final class ItemLabelGenerators {

    private ItemLabelGenerators() {
        throw new UnsupportedOperationException();
    }

    // There is no "naturalDisplay()" static factory method because you can simply pass in the method reference directly,
    // e.g., T::getDisplayName, where T implements TextuallyDisplayable.

    public static <T extends TextuallyDisplayable> @Nonnull ItemLabelGenerator<T> localizedNaturalDisplay(@Nonnull Locale locale) {
        return item -> item.getDisplayName(locale);
    }
}
