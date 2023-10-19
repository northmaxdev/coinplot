// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.fixer;

import io.github.northmaxdev.coinplot.lang.Strings;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
class FixerConfiguration { // Package-private

    @Value("${fixer.access-key}")
    private @Nullable String accessKeyValue = null;

    public @Nonnull String getAccessKey() {
        if (Strings.isNullOrBlank(accessKeyValue)) {
            throw new IllegalStateException("No Fixer API key provided");
        }
        return accessKeyValue;
    }
}
