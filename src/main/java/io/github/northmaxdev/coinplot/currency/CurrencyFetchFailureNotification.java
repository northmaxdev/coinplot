// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.currency;

import io.github.northmaxdev.coinplot.common.ui.AbstractResourceFetchFailureNotification;

public final class CurrencyFetchFailureNotification extends AbstractResourceFetchFailureNotification {

    @Override
    protected String getTextI18NPropertyKey() {
        return "resource-fetch-failure-notification.text.currencies";
    }
}
