// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frontend.core;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.frontend.i18n.I18NUtilities;

public final class LocalizedButton extends Button implements LocaleChangeObserver {

    private final String textPropertyKey;

    public LocalizedButton(String textPropertyKey) {
        this.textPropertyKey = textPropertyKey;
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        I18NUtilities.setText(this, event, textPropertyKey);
    }
}
