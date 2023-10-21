// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.common;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.IconFactory;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;
import jakarta.annotation.Nonnull;

import java.util.Objects;

public final class LocalizedButton extends Button implements LocaleChangeObserver {

    private final @Nonnull String textPropertyKey;

    public LocalizedButton(@Nonnull String textPropertyKey) {
        this.textPropertyKey = Objects.requireNonNull(textPropertyKey);
    }

    public LocalizedButton(@Nonnull String textPropertyKey, @Nonnull IconFactory iconFactory) {
        this(textPropertyKey);
        setIcon(iconFactory.create());
    }

    @Override
    public void localeChange(@Nonnull LocaleChangeEvent event) {
        I18NUtilities.setText(this, event, textPropertyKey);
    }
}
