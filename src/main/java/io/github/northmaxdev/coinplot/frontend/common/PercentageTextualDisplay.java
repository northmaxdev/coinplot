// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import io.github.northmaxdev.coinplot.lang.math.Percentage;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public final class PercentageTextualDisplay extends AbstractTextualDisplay<Percentage> {

    public PercentageTextualDisplay() {
        super();
    }

    public PercentageTextualDisplay(@Nullable Percentage item) {
        super(item);
    }

    @Override
    protected @Nonnull Icon createIcon() {
        return VaadinIcon.BOOK_PERCENT.create();
    }
}
