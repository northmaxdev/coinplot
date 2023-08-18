// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.everapi;

import io.github.northmaxdev.coinplot.backend.core.web.request.AccessKeySupplier;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class EverapiConfiguration implements AccessKeySupplier {

    // TODO: Implement this

    @Override
    public Optional<String> getAccessKey() {
        return Optional.empty();
    }
}
