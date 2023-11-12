// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import io.github.northmaxdev.coinplot.backend.core.DataProvider;
import io.github.northmaxdev.coinplot.frontend.common.AbstractTextualDisplay;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public final class DataProviderTextualDisplay extends AbstractTextualDisplay<DataProvider> {

    public DataProviderTextualDisplay() {
        super();
    }

    public DataProviderTextualDisplay(@Nullable DataProvider item) {
        super(item);
    }

    @Override
    protected @Nonnull Icon createIcon() {
        return VaadinIcon.DATABASE.create();
    }

    @Override
    protected @Nonnull String convertItemToText(@Nonnull DataProvider item) {
        return item.getDisplayName();
    }
}
