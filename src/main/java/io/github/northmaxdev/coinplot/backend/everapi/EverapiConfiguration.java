// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import io.github.northmaxdev.coinplot.backend.core.web.AccessKeyConfiguration;
import jakarta.annotation.Nullable;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class EverapiConfiguration implements AccessKeyConfiguration {

    private @Nullable String accessKey = null;

    @Override
    public Optional<String> getAccessKey() {
        return Optional.ofNullable(accessKey);
    }
}
