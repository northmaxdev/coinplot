// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.currency;

import io.github.northmaxdev.coinplot.common.DTOMapper;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Component
public final class CurrencyDTOMapper implements DTOMapper<Map<String, String>, Set<Currency>> {

    @Override
    public @Nonnull Set<Currency> map(@Nonnull Map<String, String> dto) {
        return dto.entrySet()
                .stream()
                .map(entry -> new Currency(entry.getKey(), entry.getValue()))
                .collect(toSet());
    }
}
