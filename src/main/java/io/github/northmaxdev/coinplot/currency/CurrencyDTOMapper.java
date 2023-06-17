// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.currency;

import io.github.northmaxdev.coinplot.common.web.DTOMapper;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Component
public final class CurrencyDTOMapper implements DTOMapper<Map<String, String>, Map<String, Currency>> {

    @Override
    public @Nonnull Map<String, Currency> map(@Nonnull Map<String, String> dto) {
        return dto.entrySet()
                .stream()
                .map(entry -> new Currency(entry.getKey(), entry.getValue()))
                .collect(toMap(Currency::threeLetterISOCode, Function.identity()));
    }
}
