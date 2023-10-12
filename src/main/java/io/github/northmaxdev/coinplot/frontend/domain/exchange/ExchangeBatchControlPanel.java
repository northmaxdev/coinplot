// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.domain.exchange;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.theme.lumo.LumoIcon;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import io.github.northmaxdev.coinplot.backend.core.exchange.ExchangeBatch;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public final class ExchangeBatchControlPanel extends VerticalLayout implements LocaleChangeObserver {

    private static final String SUBMIT_BUTTON_TEXT_KEY = "exchange-batch-control-panel.submit-button.text";
    private static final String CLEAR_BUTTON_TEXT_KEY = "exchange-batch-control-panel.clear-button.text";
    private static final String RELOAD_BUTTON_TEXT_KEY = "exchange-batch-control-panel.reload-button.text";

    private final ExchangeBatchAssembler assembler;
    private final Button submitButton;
    private final Button clearButton;
    private final Button reloadButton;
    private final Consumer<ExchangeBatch> onSubmit;

    public ExchangeBatchControlPanel(@Nonnull CurrencyService currencyDataSource,
                                     @Nonnull Consumer<ExchangeBatch> onSubmit) { // Must handle null inputs
        Objects.requireNonNull(currencyDataSource);
        assembler = new ExchangeBatchAssembler(currencyDataSource);

        submitButton = new Button(LumoIcon.CHECKMARK.create());
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.addClickListener(event -> submit());

        clearButton = new Button(LumoIcon.CROSS.create());
        clearButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        clearButton.addClickListener(event -> clear());

        reloadButton = new Button(LumoIcon.RELOAD.create());
        // Use the default variant for the reload button
        reloadButton.addClickListener(event -> reloadAvailableCurrencies());

        HorizontalLayout buttonBar = new HorizontalLayout(submitButton, clearButton, reloadButton);
        add(assembler, buttonBar);

        this.onSubmit = Objects.requireNonNull(onSubmit);
    }

    public void submit() {
        // TODO (Feature):
        //  An indeterminate progress bar that activates during execution of onSubmit
        //  and disables submitButton until finished
        @Nullable ExchangeBatch assembledBatch = assembler.getValue();
        onSubmit.accept(assembledBatch);
    }

    public void clear() {
        assembler.clear();
    }

    public void reloadAvailableCurrencies() {
        assembler.reloadAvailableCurrencies();
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        assembler.localeChange(event);
        I18NUtilities.setText(submitButton, event, SUBMIT_BUTTON_TEXT_KEY);
        I18NUtilities.setText(clearButton, event, CLEAR_BUTTON_TEXT_KEY);
        I18NUtilities.setText(reloadButton, event, RELOAD_BUTTON_TEXT_KEY);
    }
}
