// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.domain;

import io.github.northmaxdev.coinplot.langext.LocalDateInterval;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class FrankfurterServiceTests {

    @Autowired
    FrankfurterService service;

    // This is a half-assed test, but this should be ok for now
    @Test
    @DisplayName("Shit works")
    void works() {
        Currency euro = Currency.getInstance("EUR");
        Currency swissFranc = Currency.getInstance("CHF");
        Currency britishPound = Currency.getInstance("GBP");
        LocalDate start = LocalDate.of(2019, 1, 1);
        LocalDate end = LocalDate.of(2019, 2, 1);
        var dez = new DatedExchangeZip(
                euro,
                Set.of(swissFranc, britishPound),
                new LocalDateInterval(start, end)
        );

        ExchangeRatesDataset exchangeRates = service.getExchangeRates(dez);
        assertThat(exchangeRates.getData()).isNotEmpty();
    }
}
