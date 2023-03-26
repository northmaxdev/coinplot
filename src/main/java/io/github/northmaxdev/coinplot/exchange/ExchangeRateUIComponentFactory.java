// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.exchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class ExchangeRateUIComponentFactory {

    private final ExchangeRateService service;

    @Autowired
    public ExchangeRateUIComponentFactory(ExchangeRateService service) {
        this.service = service;
    }
}
