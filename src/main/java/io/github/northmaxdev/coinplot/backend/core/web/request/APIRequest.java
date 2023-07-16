// SPDX-License-Identifier: MIT

package io.github.northmaxdev.coinplot.backend.core.web.request;

import jakarta.annotation.Nonnull;

import java.net.URI;
import java.util.Map;

public interface APIRequest {

    @Nonnull URI toURI();

    default Map<String, String> headers() {
        return Map.of();
    }
}
