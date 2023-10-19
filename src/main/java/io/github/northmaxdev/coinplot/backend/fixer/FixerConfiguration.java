// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import io.github.northmaxdev.coinplot.lang.Strings;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.boot.context.properties.ConfigurationProperties;

// Public access allows registration via @EnableConfigurationProperties
@ConfigurationProperties(prefix = "fixer")
public record FixerConfiguration(@Nullable String accessKey) {

    public FixerConfiguration(@Nullable String accessKey) {
        this.accessKey = Strings.blankToNull(accessKey);
    }

    public @Nonnull String forcefullyGetAccessKey() {
        if (accessKey == null) {
            throw new IllegalStateException("No Fixer API key provided");
        }
        return accessKey;
    }
}
