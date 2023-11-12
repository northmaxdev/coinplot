// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import io.github.northmaxdev.coinplot.backend.core.exchange.DatelessExchange;
import io.github.northmaxdev.coinplot.frontend.common.AbstractTextualDisplay;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public final class DatelessExchangeTextualDisplay extends AbstractTextualDisplay<DatelessExchange> {

    public DatelessExchangeTextualDisplay() {
        super();
    }

    public DatelessExchangeTextualDisplay(@Nullable DatelessExchange item) {
        super(item);
    }

    @Override
    protected @Nonnull Icon createIcon() {
        return VaadinIcon.MONEY_EXCHANGE.create();
    }
}
