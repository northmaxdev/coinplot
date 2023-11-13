// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.exchange;

import io.github.northmaxdev.coinplot.backend.core.currency.Currency;
import io.github.northmaxdev.coinplot.backend.core.currency.CurrencyService;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toUnmodifiableSet;

public final class CommonExchangeRateSetDTOMapper extends AbstractExchangeRateSetDTOMapper<CommonExchangeRateSetDTO> {

    private static final Logger LOG = LoggerFactory.getLogger(CommonExchangeRateSetDTOMapper.class);

    public CommonExchangeRateSetDTOMapper(@Nonnull CurrencyService currencyDataSource) {
        super(currencyDataSource);
    }

    @Override
    public @Nonnull Set<ExchangeRate> map(@Nonnull CommonExchangeRateSetDTO dto) {
        Objects.requireNonNull(dto);

        Optional<Currency> base = getCurrency(dto.base());
        if (base.isEmpty()) {
            LOG.warn("Unknown base \"{}\", returning an empty set", dto.base());
            return Set.of();
        }

        return dto.rates()
                .entrySet()
                .stream()
                .<ExchangeRate>mapMulti((datesToRatesEntry, buffer) -> {
                    LocalDate exchangeDate = datesToRatesEntry.getKey();
                    Map<String, BigDecimal> targetCodesToValues = datesToRatesEntry.getValue();
                    targetCodesToValues.forEach((targetCode, rateValue) -> {
                        Optional<Currency> target = getCurrency(targetCode);
                        target.ifPresentOrElse(t -> {
                            ExchangeRate exchangeRate = new ExchangeRate(base.get(), t, exchangeDate, rateValue);
                            buffer.accept(exchangeRate);
                            // NOTE: This logs a warning on EVERY single missing target,
                            // which may (but realistically usually won't) flood the log.
                        }, () -> LOG.warn("Unknown target \"{}\", skipping", targetCode));
                    });
                })
                .collect(toUnmodifiableSet());
    }
}
