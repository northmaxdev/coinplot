// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.frankfurter;

import jakarta.annotation.Nullable;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.boot.context.properties.ConfigurationProperties;

// Public access allows registration via @EnableConfigurationProperties
@ConfigurationProperties(prefix = "frankfurter")
public record FrankfurterConfiguration(@Nullable HttpHost customHost) {}
