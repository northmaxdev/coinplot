// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.common.ui;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import io.github.northmaxdev.coinplot.common.fn.ExceptionHandler;
import jakarta.annotation.Nonnull;

import java.time.Duration;

public abstract class AbstractResourceFetchFailureNotification extends Notification
        implements LocaleChangeObserver, ExceptionHandler {

    private static final Duration DEFAULT_DURATION = Duration.ofSeconds(5);

    protected AbstractResourceFetchFailureNotification() {
        setTranslatedText();
        addThemeVariants(NotificationVariant.LUMO_ERROR);
        setPosition(Position.BOTTOM_CENTER);
        setDuration((int) DEFAULT_DURATION.toMillis()); // This is safe
    }

    @Override
    public final void handle(Exception e) {
        open();
    }

    //////////
    // i18n //
    //////////

    @Override
    public final void localeChange(LocaleChangeEvent event) {
        setTranslatedText();
    }

    protected abstract @Nonnull String getTextI18NPropertyKey();

    private void setTranslatedText() {
        @Nonnull String text = getTranslation(getTextI18NPropertyKey());
        setText(text);
    }
}
