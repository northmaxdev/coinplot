// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.backend.core.exchange.DatelessExchange;
import io.github.northmaxdev.coinplot.frontend.common.AbstractTextualDisplay;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public final class DatelessExchangeTextualDisplay
        extends AbstractTextualDisplay<DatelessExchange>
        implements LocaleChangeObserver {

    private static final String LABEL_KEY = "dateless-exchange-textual-display.label";

    public DatelessExchangeTextualDisplay() {
        super();
    }

    public DatelessExchangeTextualDisplay(@Nullable DatelessExchange item) {
        super(item);
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        I18NUtilities.setLabel(this, event, LABEL_KEY);
    }

    @Override
    protected @Nonnull Icon createIcon() {
        return VaadinIcon.MONEY_EXCHANGE.create();
    }
}
