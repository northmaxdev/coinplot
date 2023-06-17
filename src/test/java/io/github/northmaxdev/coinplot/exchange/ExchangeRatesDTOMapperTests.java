// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.exchange;

import io.github.northmaxdev.coinplot.currency.Currency;
import io.github.northmaxdev.coinplot.currency.CurrencyService;
import jakarta.annotation.Nullable;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ExchangeRatesDTOMapperTests {

    // Mock the service like this because life is simply too short to be dealing with Mockito and Spring context stuff
    static final class CurrencyServiceMock implements CurrencyService {

        @Override
        public Set<Currency> getAvailableCurrencies() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<Currency> getCurrency(@Nullable String threeLetterISOCode) {
            return Optional.ofNullable(threeLetterISOCode)
                    .map(s -> new Currency(s, ""));
        }
    }

    CurrencyService currencyService = new CurrencyServiceMock();

    @Test
    void maps() {
        var dto = new ExchangeRatesDTO("EUR", Map.of(
                LocalDate.of(2005, 3, 1), Map.of("CHF", 1.5357, "GBP", 0.6879),
                LocalDate.of(2005, 3, 3), Map.of("CHF", 1.5461, "GBP", 0.6878)
        ));
        var mapper = new ExchangeRatesDTOMapper(currencyService);

        Currency eur = new Currency("EUR", "");
        Currency chf = new Currency("CHF", "");
        Currency gbp = new Currency("GBP", "");
        Collection<ExchangeRate> expected = List.of(
                new ExchangeRate(eur, chf, LocalDate.of(2005, 3, 1), 1.5357),
                new ExchangeRate(eur, gbp, LocalDate.of(2005, 3, 1), 0.6879),
                new ExchangeRate(eur, chf, LocalDate.of(2005, 3, 3), 1.5461),
                new ExchangeRate(eur, gbp, LocalDate.of(2005, 3, 3), 0.6878)
        );
        Collection<ExchangeRate> actual = mapper.map(dto);

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
