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

    protected abstract @Nonnull String convertItemToText(@Nonnull T item);

    public final void display(@Nullable T item) {
        if (item == null) {
            clear();
        } else {
            String text = convertItemToText(item);
            setValue(text);
        }
    }
}
