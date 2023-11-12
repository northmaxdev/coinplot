// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.textfield.TextField;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public abstract class AbstractTextualDisplay<T> extends TextField {

    protected AbstractTextualDisplay() {
        setReadOnly(true);
        setPrefixComponent(createIcon());
    }

    protected AbstractTextualDisplay(@Nullable T item) {
        this();
        display(item);
    }

    protected abstract @Nonnull Icon createIcon();

    // Non-final, default implementation.
    // This is OK only if T::toString promises to be human-readable and/or UI-suitable.
    // A more dedicated method is recommended otherwise (e.g. getName(), getDisplayName(), format(), etc.)
    protected @Nonnull String convertItemToText(@Nonnull T item) {
        return item.toString();
    }

    public final void display(@Nullable T item) {
        if (item == null) {
            clear();
        } else {
            String text = convertItemToText(item);
            setValue(text);
        }
    }
}
