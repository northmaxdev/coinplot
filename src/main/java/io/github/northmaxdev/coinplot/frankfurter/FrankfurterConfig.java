// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.frankfurter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.Nullable;

@ConfigurationProperties(prefix = "frankfurter")
public record FrankfurterConfig(String host) {

    public FrankfurterConfig(@Nullable String host) {
        if (host == null || host.isBlank()) {
            throw new IllegalStateException("must specify Frankfurter API host in config");
        }
        this.host = host;
    }
}
