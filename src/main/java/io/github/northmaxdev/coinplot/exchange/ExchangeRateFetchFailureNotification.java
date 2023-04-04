// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.exchange;

import io.github.northmaxdev.coinplot.common.ui.AbstractResourceFetchFailureNotification;

public final class ExchangeRateFetchFailureNotification extends AbstractResourceFetchFailureNotification {

    @Override
    protected String getTextI18NPropertyKey() {
        return "resource-fetch-failure-notification.text.exchange-rates";
    }
}
