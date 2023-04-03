// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.common.ui;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;

import java.time.Duration;

public final class ResourceFetchFailureNotification extends Notification implements LocaleChangeObserver {

    private static final Duration DEFAULT_DURATION = Duration.ofSeconds(5);

    private final String i18nPropertyKey;

    public static ResourceFetchFailureNotification forCurrencies() {
        return new ResourceFetchFailureNotification("fetch-failure-notif.text.currencies");
    }

    public static ResourceFetchFailureNotification forExchangeRates() {
        return new ResourceFetchFailureNotification("fetch-failure-notif.text.exchange-rates");
    }

    private ResourceFetchFailureNotification(String i18nPropertyKey) {
        this.i18nPropertyKey = i18nPropertyKey;

        addThemeVariants(NotificationVariant.LUMO_ERROR);
        setTranslatedText();
        setDuration((int) DEFAULT_DURATION.toMillis()); // This is safe
        setPosition(Position.BOTTOM_CENTER);
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        setTranslatedText();
    }

    private void setTranslatedText() {
        setText(getTranslation(i18nPropertyKey));
    }
}
