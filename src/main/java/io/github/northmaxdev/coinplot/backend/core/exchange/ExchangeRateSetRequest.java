// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.web.request.APIRequest;
import jakarta.annotation.Nonnull;

public interface ExchangeRateSetRequest extends APIRequest {

    @Nonnull ExchangeBatch getRequestData();
}
