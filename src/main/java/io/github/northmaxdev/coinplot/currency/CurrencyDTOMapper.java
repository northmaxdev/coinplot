// SPDX-License-Identifier: Unlicense

package io.github.northmaxdev.coinplot.currency;

import io.github.northmaxdev.coinplot.common.DTOMapper;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public final class CurrencyDTOMapper implements DTOMapper<Map<String, String>, List<Currency>> {

    @Override
    public @Nonnull List<Currency> map(@Nonnull Map<String, String> dto) {
        return dto.entrySet()
                .stream()
                .map(entry -> new Currency(entry.getKey(), entry.getValue()))
                .toList();
    }
}
