// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core;

import io.github.northmaxdev.coinplot.lang.Strings;
import jakarta.annotation.Nullable;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Optional;

@ConfigurationProperties(prefix = "data-provider")
public record DataProviderConfiguration(@Nullable String selectedProviderID) {

    public DataProviderConfiguration(@Nullable String selectedProviderID) {
        this.selectedProviderID = Strings.blankToNull(selectedProviderID);
    }

    public Optional<String> safelyGetSelectedProviderID() {
        return Optional.ofNullable(selectedProviderID);
    }
}
